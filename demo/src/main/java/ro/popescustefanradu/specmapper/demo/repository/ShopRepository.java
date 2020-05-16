package ro.popescustefanradu.specmapper.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.popescustefanradu.specmapper.demo.entities.Shop;

public interface ShopRepository extends JpaRepository<Shop, String>, JpaSpecificationExecutor<Shop> {
}
