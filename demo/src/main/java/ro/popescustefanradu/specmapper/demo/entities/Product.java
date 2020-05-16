package ro.popescustefanradu.specmapper.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Product {
    @Id
    private String id;

    private String name;

    private String ean;

    private LocalDateTime creationTime;

    private LocalDateTime updateTime;
}
