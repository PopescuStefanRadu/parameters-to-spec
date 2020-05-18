package ro.popescustefanradu.specmapper.mapper;

import lombok.RequiredArgsConstructor;
import ro.popescustefanradu.specmapper.mapper.condition.*;

import java.util.List;

@RequiredArgsConstructor
public enum RawOperator {
    EQUAL(List.of("Equal", "Eq", ""), ObjectSimpleFilterCondition.ObjectFilterType.EQUAL),
    NOT_EQUAL(List.of("NotEqual", "Ne"), ObjectSimpleFilterCondition.ObjectFilterType.NOT_EQUAL),

    LIKE(List.of("Like"), StringFilterCondition.StringFilterType.LIKE),
    NOT_LIKE(List.of("NotLike"), StringFilterCondition.StringFilterType.NOT_LIKE),
    LIKE_IGNORE_CASE(List.of("LikeIgnoreCase", "ILike"), StringFilterCondition.StringFilterType.LIKE_IGNORE_CASE),
    NOT_LIKE_IGNORE_CASE(List.of("NotLikeIgnoreCase", "INotLike"), StringFilterCondition.StringFilterType.NOT_LIKE_IGNORE_CASE),
    STARTS_WITH(List.of("StartsWith"), StringFilterCondition.StringFilterType.STARTS_WITH),
    STARTS_WITH_IGNORE_CASE(List.of("StartsWithIgnoreCase", "IStartsWith"), StringFilterCondition.StringFilterType.STARTS_WITH_IGNORE_CASE),
    ENDS_WITH(List.of("EndsWith"), StringFilterCondition.StringFilterType.ENDS_WITH),
    ENDS_WITH_IGNORE_CASE(List.of("EndsWithIgnoreCase", "IEndsWith"), StringFilterCondition.StringFilterType.ENDS_WITH_IGNORE_CASE),

    LESS_THAN(List.of("LessThan", "Lt"), ComparableSimpleFilterCondition.ComparableFilterType.LESS_THAN),
    LESS_THAN_OR_EQUAL_TO(List.of("LessThanOrEqualTo", "LessOrEqual", "Le"), ComparableSimpleFilterCondition.ComparableFilterType.LESS_THAN_OR_EQUAL),
    GREATER_THAN(List.of("GreaterThan", "Gt"), ComparableSimpleFilterCondition.ComparableFilterType.GREATER_THAN),
    GREATER_THAN_OR_EQUAL_TO(List.of("GreaterThanOrEqualTo", "GreaterOrEqual", "Ge"), ComparableSimpleFilterCondition.ComparableFilterType.GREATER_THAN_OR_EQUAL),
    BETWEEN(List.of("Between"), ComparableCompositeFilterCondition.ComparableCompositeFilterType.BETWEEN),

    IN(List.of("In"), SimpleCompositeFilterCondition.ListParamFilterType.IN),
    NOT_IN(List.of("NotIn"), SimpleCompositeFilterCondition.ListParamFilterType.NOT_IN),

    // TODO
    CONTAINS(List.of("Contains"), null);

    public final List<String> suffixes;
    public final Object filterType;
}
