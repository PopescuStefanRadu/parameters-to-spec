package ro.popescustefanradu.specmapper.demo.entities;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class ShopProduct {

    @Id
    @EmbeddedId
    @JsonUnwrapped
    private PK pk;

    private BigDecimal price;

    @Data
    @Embeddable
    public static class PK implements Serializable {

        @ManyToOne
        private Shop shop;
        @ManyToOne
        private Product product;
    }
}
