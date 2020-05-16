package ro.popescustefanradu.specmapper.mapper.condition;

import ro.popescustefanradu.specmapper.mapper.FilterCondition;

import java.util.List;

public abstract class CompositeFilterCondition<T> implements FilterCondition<T> {
    protected List<T> values;
}
