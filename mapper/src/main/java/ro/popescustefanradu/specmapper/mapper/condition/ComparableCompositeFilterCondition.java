package ro.popescustefanradu.specmapper.mapper.condition;

import org.springframework.data.jpa.domain.Specification;
import ro.popescustefanradu.specmapper.mapper.ExpressionQualifier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class ComparableCompositeFilterCondition<T extends Comparable<T>> extends CompositeFilterCondition<T> {
    ComparableCompositeFilterType filterType;

    public <ENTITY> Specification<ENTITY> toSpec(ExpressionQualifier<T> expression) {
        return (root, query, criteriaBuilder) ->
                filterType.toPredicate(expression.getExpression(root, query), criteriaBuilder, values);
    }

    public enum ComparableCompositeFilterType {
        BETWEEN {
            @Override
            public <T extends Comparable<T>> Predicate toPredicate(Expression<? extends T> expression, CriteriaBuilder criteriaBuilder, List<T> value) {
                return criteriaBuilder.between(expression, value.get(0), value.get(1));
            }
        };

        public abstract <T extends Comparable<T>> Predicate toPredicate(Expression<? extends T> expression, CriteriaBuilder criteriaBuilder, List<T> value);
    }
}
