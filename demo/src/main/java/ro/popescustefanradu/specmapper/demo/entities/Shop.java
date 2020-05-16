package ro.popescustefanradu.specmapper.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Shop {
    @Id
    private String id;

    private String name;
}
