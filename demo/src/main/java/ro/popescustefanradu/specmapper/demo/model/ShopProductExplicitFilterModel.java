package ro.popescustefanradu.specmapper.demo.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopProductExplicitFilterModel {
    private final BigDecimal price;
    private final BigDecimal priceLessThan;
    private final BigDecimal priceLessOrEqualTo;
    private final BigDecimal priceGreaterThan;
    private final BigDecimal priceGreaterOrEqualTo;
}
