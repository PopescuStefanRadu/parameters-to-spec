package ro.popescustefanradu.specmapper.mapper.condition;

import lombok.Data;
import ro.popescustefanradu.specmapper.mapper.FilterCondition;

import java.util.List;

@Data
public abstract class CompositeFilterCondition<T> implements FilterCondition<T> {
    protected final List<T> values;
}
