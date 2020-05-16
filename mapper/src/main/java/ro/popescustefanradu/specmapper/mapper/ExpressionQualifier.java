package ro.popescustefanradu.specmapper.mapper;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 * Path to a query expression to used to map to {@link FilterCondition}s in
 * {@link FilterField}
 */
@FunctionalInterface
public interface ExpressionQualifier<T> {
    /**
     * Supply an expression on which {@link FilterCondition}s in {@link FilterField} are checked against
     * @param root root of the main entity
     * @return Query expression
     */
    Expression<T> getExpression(Root<?> root, CriteriaQuery<?> query);
}
