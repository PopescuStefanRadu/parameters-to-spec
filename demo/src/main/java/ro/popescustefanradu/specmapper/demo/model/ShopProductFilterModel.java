package ro.popescustefanradu.specmapper.demo.model;

import lombok.Data;
import ro.popescustefanradu.specmapper.mapper.FilterField;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Limitations:
 * Only FilterField fields
 * No nesting
 * Constructor needs FilterField parameters and will use parameter names to match request
 * Needs setters
 */
@Data
public class ShopProductFilterModel {
    private final FilterField<String> productName;
    // todo add custom formatting via existing @DateTimeFormat
    private final FilterField<LocalDateTime> productCreationTime;
    private final FilterField<String> productEan;
    private final FilterField<BigDecimal> price;
    private final FilterField<String> shopName;
}
