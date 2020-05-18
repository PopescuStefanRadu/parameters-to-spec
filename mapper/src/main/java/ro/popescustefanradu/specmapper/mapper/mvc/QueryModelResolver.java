package ro.popescustefanradu.specmapper.mapper.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ro.popescustefanradu.specmapper.mapper.FilterCondition;
import ro.popescustefanradu.specmapper.mapper.FilterField;
import ro.popescustefanradu.specmapper.mapper.condition.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ro.popescustefanradu.specmapper.mapper.RawOperator.*;

@Slf4j
public class QueryModelResolver implements HandlerMethodArgumentResolver {
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(QueryModel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        Constructor<?> ctor = getPreferredConstructor(parameter);
        Object attribute = constructAttribute(ctor, parameter.getParameterName(), binderFactory, webRequest);
        return attribute;
    }

    private Constructor<?> getPreferredConstructor(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getNestedParameterType();
        Constructor<?> ctor = BeanUtils.findPrimaryConstructor(clazz);
        if (ctor == null) {
            Constructor<?>[] ctors = clazz.getConstructors();
            if (ctors.length == 1) {
                ctor = ctors[0];
            } else {
                try {
                    ctor = clazz.getDeclaredConstructor();
                } catch (NoSuchMethodException ex) {
                    throw new IllegalStateException("No primary or default constructor found for " + clazz, ex);
                }
            }
        }
        return ctor;
    }

    private Object constructAttribute(Constructor<?> ctor,
                                      String parameterName,
                                      WebDataBinderFactory webDataBinderFactory,
                                      NativeWebRequest webRequest) throws Exception {

        if (ctor.getParameterCount() == 0) {
            return BeanUtils.instantiateClass(ctor);
        }

        String[] paramNames = parameterNameDiscoverer.getParameterNames(ctor);
        Assert.state(paramNames != null, () -> "Cannot resolve parameter names for constructor " + ctor);
        Class<?>[] paramTypes = ctor.getParameterTypes();
        Assert.state(paramNames.length == paramTypes.length,
                () -> "Invalid number of parameter names: " + paramNames.length + " for constructor " + ctor);
        checkParamTypesAreFilterFields(paramTypes);
        Class<?>[] filterConditionGenericTypes = getFilterConditionGenericTypes(ctor.getGenericParameterTypes());

        WebDataBinder webDataBinder = webDataBinderFactory.createBinder(webRequest, null, parameterName);

        FilterField<?>[] args = new FilterField[paramTypes.length];
        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            Class<?> filterConditionType = filterConditionGenericTypes[i];
            List<RawCondition> rawConditions = OperatorMappings.getConditions(filterConditionType, paramName);
            args[i] = getFilterField(webRequest, webDataBinder, filterConditionType, rawConditions);
        }
        return BeanUtils.instantiateClass(ctor, args);
    }

    private void checkParamTypesAreFilterFields(Class<?>[] paramTypes) {
        for (Class<?> paramType : paramTypes) {
            if (!paramType.equals(FilterField.class)) {
                throw new IllegalArgumentException(String.format(
                        "@%s handling supports only %s. Use @ModelAttribute instead",
                        QueryModel.class.getSimpleName(), FilterField.class.getSimpleName()));
            }
        }
    }

    private Class<?>[] getFilterConditionGenericTypes(Type[] genericParams) {
        Class<?>[] genericTypes = new Class[genericParams.length];
        for (int i = 0; i < genericParams.length; i++) {
            Type genericType = ((ParameterizedType) genericParams[i]).getActualTypeArguments()[0];
            genericTypes[i] = (Class<?>) genericType;
        }
        return genericTypes;
    }

    private <T> FilterField<T> getFilterField(NativeWebRequest webRequest,
                                              WebDataBinder webDataBinder,
                                              Class<T> filterConditionType,
                                              List<RawCondition> conditions) {

        List<FilterCondition<T>> filterConditions = new ArrayList<>();
        for (RawCondition condition : conditions) {
            Optional<? extends FilterCondition<T>> conditionOptional = createCondition(filterConditionType, condition, webRequest, webDataBinder);
            conditionOptional.ifPresent(filterConditions::add);
        }
        return new FilterField<>(filterConditions);
    }

    private <T> Optional<FilterCondition<T>> createCondition(Class<T> filterConditionType,
                                                             RawCondition rawCondition,
                                                             NativeWebRequest webRequest,
                                                             WebDataBinder webDataBinder) {
        String[] rawParams = webRequest.getParameterValues(rawCondition.getParamName());
        if (rawParams == null) {
            return Optional.empty();
        }
        if (rawParams.length != 1) {
            log.warn("Multiple values for param name not supported. Param name: {}", rawCondition.getParamName());
        }
        var operator = rawCondition.getOperator();
        if (operator == IN || operator == NOT_IN) {
            String[] splitRawValues = rawParams[0].split(",");
            List<T> values = convertArray(splitRawValues, filterConditionType, webDataBinder, rawCondition);
            return Optional.of(new SimpleCompositeFilterCondition<>((SimpleCompositeFilterCondition.ListParamFilterType) operator.filterType, values));
        }

        if (operator.equals(BETWEEN) || operator.equals(CONTAINS)) {
            if (Comparable.class.isAssignableFrom(filterConditionType)) {
                String[] splitRawValues = rawParams[0].split(",");
                // todo remove empty strings?
                List<T> values = convertArray(splitRawValues, filterConditionType, webDataBinder, rawCondition);
                ComparableCompositeFilterCondition.ComparableCompositeFilterType filterType = (ComparableCompositeFilterCondition.ComparableCompositeFilterType) operator.filterType;
                return Optional.of((FilterCondition<T>) new ComparableCompositeFilterCondition(filterType, values));
            }
            // TODO contains
        }

        // todo research cleaner solution
        if (String.class.isAssignableFrom(filterConditionType)) {
            try {
                String value = webDataBinder.convertIfNecessary(rawParams[0], String.class);
                FilterCondition<T> filterCondition = (FilterCondition<T>) new StringFilterCondition((StringFilterCondition.StringFilterType) operator.filterType, value);
                return Optional.of(filterCondition);
            } catch (TypeMismatchException e) {
                log.error("Wrong type in query for {}, param value: {}", rawCondition, rawParams[0], e);
                return Optional.empty();
            }
        }

        if (Comparable.class.isAssignableFrom(filterConditionType)) {
            try {
                T value = webDataBinder.convertIfNecessary(rawParams[0], filterConditionType);
                return Optional.of((FilterCondition<T>) new ComparableSimpleFilterCondition((ComparableSimpleFilterCondition.ComparableFilterType) operator.filterType, (Comparable) value));
            } catch (TypeMismatchException e) {
                log.error("Wrong type in query for {}, param value: {}", rawCondition, rawParams[0], e);
                return Optional.empty();
            }
        }

        try {
            T value = webDataBinder.convertIfNecessary(rawParams[0], filterConditionType);
            return Optional.of((FilterCondition<T>) new ObjectSimpleFilterCondition((ObjectSimpleFilterCondition.ObjectFilterType) operator.filterType, value));
        } catch (TypeMismatchException e) {
            log.error("Wrong type in query for {}, param value: {}", rawCondition, rawParams[0], e);
            return Optional.empty();
        }
    }

    private <T> List<T> convertArray(String[] values, Class<T> clazz, WebDataBinder binder, RawCondition condition) {
        List<T> convertedValues = new ArrayList<>();
        boolean bindingFailure = false;
        for (String value : values) {
            try {
                convertedValues.add(binder.convertIfNecessary(value, clazz));
            } catch (TypeMismatchException e) {
                bindingFailure = true;
            }
        }
        if (bindingFailure) {
            log.error("Wrong type in query for {}, param value: {}", condition, values);
        }
        return convertedValues;
    }
}
