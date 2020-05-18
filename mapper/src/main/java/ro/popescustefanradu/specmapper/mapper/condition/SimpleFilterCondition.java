package ro.popescustefanradu.specmapper.mapper.condition;

import lombok.Data;
import ro.popescustefanradu.specmapper.mapper.FilterCondition;

@Data
public abstract class SimpleFilterCondition<T> implements FilterCondition<T> {
    protected final T value;
}
