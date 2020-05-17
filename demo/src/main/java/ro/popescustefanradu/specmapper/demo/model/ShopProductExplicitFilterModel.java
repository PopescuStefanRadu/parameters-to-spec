package ro.popescustefanradu.specmapper.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopProductExplicitFilterModel {
    private BigDecimal price;
    private BigDecimal priceLessThan;
    private BigDecimal priceLessOrEqualTo;
    private BigDecimal priceGreaterThan;
    private BigDecimal priceGreaterOrEqualTo;
}
