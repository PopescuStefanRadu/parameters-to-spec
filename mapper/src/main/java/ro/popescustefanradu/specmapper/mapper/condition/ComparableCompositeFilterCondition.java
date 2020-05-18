package ro.popescustefanradu.specmapper.mapper.condition;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;
import ro.popescustefanradu.specmapper.mapper.ExpressionQualifier;

import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComparableCompositeFilterCondition<T extends Comparable<T>> extends CompositeFilterCondition<T> {
    private final ComparableCompositeFilterType filterType;

    public ComparableCompositeFilterCondition(ComparableCompositeFilterType filterType, List<T> values) {
        super(values);
        this.filterType = filterType;
        // todo validation of value.get(0) && value.get(1) ??
    }

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
