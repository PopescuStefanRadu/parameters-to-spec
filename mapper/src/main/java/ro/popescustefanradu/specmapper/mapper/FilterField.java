package ro.popescustefanradu.specmapper.mapper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * FilterField that AND's all conditions
 */
@AllArgsConstructor
@EqualsAndHashCode
public class FilterField<T> {
    private final List<FilterCondition<T>> filterConditions;

    public <ENTITY> Specification<ENTITY> toSpec(ExpressionQualifier<T> expressionQualifier) {
        return filterConditions.stream()
                .map(tFilterCondition -> tFilterCondition.<ENTITY>toSpec(expressionQualifier))
                .reduce(Specification.where(null), Specification::and);
    }
}
