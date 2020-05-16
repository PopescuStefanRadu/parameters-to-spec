package ro.popescustefanradu.specmapper.demo.model;

import lombok.Data;
import ro.popescustefanradu.specmapper.mapper.FilterField;

import java.math.BigDecimal;

@Data
public class TestFilterModel {
    FilterField<String> name;
    FilterField<BigDecimal> value;
}
