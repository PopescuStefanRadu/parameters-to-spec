package ro.popescustefanradu.specmapper.mapper.condition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;
import ro.popescustefanradu.specmapper.mapper.ExpressionQualifier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SimpleCompositeFilterCondition<T> extends CompositeFilterCondition<T> {
    private final ListParamFilterType filterType;

    public SimpleCompositeFilterCondition(ListParamFilterType filterType, List<T> value) {
        super(value);
        this.filterType = filterType;
    }

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
        },
        NOT_IN {
            @Override
            public <T> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, List<T> value) {
                return expression.in(value).not();
            }
        };

        public abstract <T> Predicate toPredicate(Expression<T> expression, CriteriaBuilder criteriaBuilder, List<T> value);
    }
}
