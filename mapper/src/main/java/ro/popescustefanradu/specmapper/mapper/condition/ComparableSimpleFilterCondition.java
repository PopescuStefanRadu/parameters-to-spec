package ro.popescustefanradu.specmapper.mapper.condition;

import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import ro.popescustefanradu.specmapper.mapper.ExpressionQualifier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ComparableSimpleFilterCondition<T extends Comparable<T>> extends SimpleFilterCondition<T> {
    @NonNull
    ComparableFilterType filterType;

    @Override
    public <ENTITY> Specification<ENTITY> toSpec(ExpressionQualifier<T> expression) {
        return (root, query, criteriaBuilder) ->
                filterType.toPredicate(expression.getExpression(root, query), criteriaBuilder, value);
    }

    public enum ComparableFilterType {
        LESS_THAN {
            @Override
            public <T extends Comparable<T>> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, T value) {
                return criteriaBuilder.lessThan(expression, value);
            }
        },
        LESS_THAN_OR_EQUAL {
            @Override
            public <T extends Comparable<T>> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, T value) {
                return criteriaBuilder.lessThanOrEqualTo(expression, value);
            }
        },
        GREATER_THAN {
            @Override
            public <T extends Comparable<T>> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, T value) {
                return criteriaBuilder.greaterThan(expression, value);
            }
        },
        GREATER_THAN_OR_EQUAL {
            @Override
            public <T extends Comparable<T>> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, T value) {
                return criteriaBuilder.greaterThanOrEqualTo(expression, value);
            }
        };

        public abstract <T extends Comparable<T>> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, T value);
    }
}
