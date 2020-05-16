package ro.popescustefanradu.specmapper.mapper.condition;

import org.springframework.data.jpa.domain.Specification;
import ro.popescustefanradu.specmapper.mapper.ExpressionQualifier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class SimpleCompositeFilterCondition<T> extends CompositeFilterCondition<T> {
    ListParamFilterType filterType;

    @Override
    public <ENTITY> Specification<ENTITY> toSpec(ExpressionQualifier<T> expression) {
        return (root, query, criteriaBuilder) ->
                filterType.toPredicate(expression.getExpression(root, query), criteriaBuilder, values);
    }

    public enum ListParamFilterType {
        IN {
            @Override
            public <T> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, List<T> value) {
                return expression.in(value);
            }
        };

        public abstract <T> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, List<T> value);
    }
}
