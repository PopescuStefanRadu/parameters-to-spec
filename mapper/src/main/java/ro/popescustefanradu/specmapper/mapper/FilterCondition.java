package ro.popescustefanradu.specmapper.mapper;

import org.springframework.data.jpa.domain.Specification;

@FunctionalInterface
public interface FilterCondition<T> {
    <ENTITY> Specification<ENTITY> toSpec(ExpressionQualifier<T> expressionQualifier);
}
