package ro.popescustefanradu.specmapper.demo.resources;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.popescustefanradu.specmapper.demo.entities.ShopProduct;
import ro.popescustefanradu.specmapper.demo.model.ShopProductExplicitFilterModel;
import ro.popescustefanradu.specmapper.demo.model.ShopProductFilterModel;
import ro.popescustefanradu.specmapper.demo.repository.ShopProductRepository;
import ro.popescustefanradu.specmapper.mapper.mvc.QueryModel;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final ShopProductRepository shopProductRepository;

    @GetMapping("hello-world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("shop-product")
    public List<ShopProduct> getFiltered(@QueryModel ShopProductFilterModel filterModel) {
        List<Specification<ShopProduct>> specs = List.of(
                filterModel.getProductName().toSpec((root, query) -> root.get("pk").get("product").get("name")),
                filterModel.getProductCreationTime().toSpec((root, query) -> root.get("pk").get("product").get("creationTime")),
                filterModel.getProductEan().toSpec((root, query) -> root.get("pk").get("product").get("ean")),
                filterModel.getShopName().toSpec((root, query) -> root.get("pk").get("shop").get("name")),
                filterModel.getPrice().toSpec((root, query) -> root.get("price"))
        );
        return shopProductRepository.findAll(specs.stream().reduce(Specification.where(null), Specification::and));
    }

    @GetMapping("explicit-filter")
    public ShopProductExplicitFilterModel testExplicitFilter(@ModelAttribute ShopProductExplicitFilterModel explicitFilter) {
        return explicitFilter;
    }

    @GetMapping("query-params")
    public Map<String, List<String>> showQueryParams(@RequestParam(required = false) MultiValueMap<String, String> params) {
        return params;
    }
}
