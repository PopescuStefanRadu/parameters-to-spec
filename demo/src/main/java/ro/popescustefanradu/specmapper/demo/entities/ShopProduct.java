package ro.popescustefanradu.specmapper.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class ShopProduct {

    @Id
    @EmbeddedId
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
