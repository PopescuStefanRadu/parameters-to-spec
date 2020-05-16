package ro.popescustefanradu.specmapper.demo.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.popescustefanradu.specmapper.demo.entities.Product;
import ro.popescustefanradu.specmapper.demo.entities.ShopProduct;
import ro.popescustefanradu.specmapper.demo.repository.ProductRepository;
import ro.popescustefanradu.specmapper.demo.model.TestFilterModel;
import ro.popescustefanradu.specmapper.demo.repository.ShopProductRepository;
import ro.popescustefanradu.specmapper.mapper.mvc.QueryModel;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final ProductRepository productRepository;
    private final ShopProductRepository shopProductRepository;

    @GetMapping("hello-world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("all-shop-products")
    public List<ShopProduct> allShopProducts() {
        return this.shopProductRepository.findAll();
    }

    @GetMapping("converter")
    public List<Product> getFiltered(@QueryModel TestFilterModel filterModel) {
        Specification<Product> nameSpecs = filterModel.getName().toSpec((root, query) -> root.get("name"));
        Specification<Product> valueSpecs = filterModel.getValue().toSpec((root, query) -> root.get("value"));
        return productRepository.findAll(nameSpecs.and(valueSpecs));
    }


    @GetMapping("query-params")
    public Map<String, List<String>> showQueryParams(@RequestParam(required = false) MultiValueMap<String, String> params) {
        return params;
    }
}
