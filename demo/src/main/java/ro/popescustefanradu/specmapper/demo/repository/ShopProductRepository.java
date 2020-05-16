package ro.popescustefanradu.specmapper.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ro.popescustefanradu.specmapper.demo.entities.ShopProduct;

public interface ShopProductRepository extends JpaRepository<ShopProduct, String>, JpaSpecificationExecutor<ShopProduct> {
}
