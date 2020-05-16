package ro.popescustefanradu.specmapper.mapper.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.popescustefanradu.specmapper.mapper.FilterCondition;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SimpleFilterCondition<T> implements FilterCondition<T> {
    protected T value;
}
