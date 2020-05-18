package ro.popescustefanradu.specmapper.mapper.condition;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import ro.popescustefanradu.specmapper.mapper.ExpressionQualifier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ObjectSimpleFilterCondition<T> extends SimpleFilterCondition<T> {
    private final ObjectFilterType filterType;

    public ObjectSimpleFilterCondition(ObjectFilterType filterType, T value) {
        super(value);
        this.filterType = filterType;
    }

    @Override
    public <ENTITY> Specification<ENTITY> toSpec(ExpressionQualifier<T> expression) {
        return (root, query, criteriaBuilder) ->
                filterType.toPredicate(expression.getExpression(root, query), criteriaBuilder, value);
    }

    public enum ObjectFilterType {
        EQUAL {
            @Override
            public <T> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, T value) {
                return criteriaBuilder.equal(expression, value);
            }
        },
        NOT_EQUAL {
            @Override
            public <T> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, T value) {
                return criteriaBuilder.notEqual(expression, value);
            }
        };

        public abstract <T> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, T value);
    }
}
