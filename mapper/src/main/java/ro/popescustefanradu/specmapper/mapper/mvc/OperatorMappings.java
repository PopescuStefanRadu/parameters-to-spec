package ro.popescustefanradu.specmapper.mapper.mvc;

import ro.popescustefanradu.specmapper.mapper.RawOperator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;

import static ro.popescustefanradu.specmapper.mapper.RawOperator.*;

public final class OperatorMappings {
    private OperatorMappings() {}

    static final List<RawOperator> comparableOperators = List.of(GREATER_THAN, GREATER_THAN_OR_EQUAL_TO,
            LESS_THAN, LESS_THAN_OR_EQUAL_TO, BETWEEN,
            EQUAL, NOT_EQUAL, IN, NOT_IN);

    static final List<RawOperator> stringOperators = List.of(LIKE, NOT_LIKE, LIKE_IGNORE_CASE, NOT_LIKE_IGNORE_CASE,
            STARTS_WITH, STARTS_WITH_IGNORE_CASE, ENDS_WITH, ENDS_WITH_IGNORE_CASE,
            EQUAL, NOT_EQUAL, IN, NOT_IN);

    static final List<RawOperator> objectOperators = List.of(EQUAL, NOT_EQUAL, IN, NOT_IN);

    static List<RawCondition> getConditions(Class<?> clazz, String baseParamName) {
        List<RawOperator> operators = getMatchingOperators(clazz);
        List<RawCondition> conditions = new ArrayList<>();
        for (RawOperator operator : operators) {
            for (String suffix : operator.suffixes) {
                conditions.add(new RawCondition(baseParamName + suffix, operator));
            }
        }
        return conditions;
    }

    private static List<RawOperator> getMatchingOperators(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return OperatorMappings.stringOperators;
        } else if (Comparable.class.isAssignableFrom(clazz)) {
            return OperatorMappings.comparableOperators;
        }
        return OperatorMappings.objectOperators;
    }
}
