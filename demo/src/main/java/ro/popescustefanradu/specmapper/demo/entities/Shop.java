package ro.popescustefanradu.specmapper.demo.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Shop {
    @Id
    private String id;

    private String name;
}
