package ro.popescustefanradu.specmapper.mapper.mvc;

import lombok.Data;
import ro.popescustefanradu.specmapper.mapper.RawOperator;

@Data
public class RawCondition {
    private final String paramName;
    private final RawOperator operator;
}
