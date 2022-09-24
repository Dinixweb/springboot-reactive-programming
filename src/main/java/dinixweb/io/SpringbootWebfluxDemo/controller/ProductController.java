package dinixweb.io.SpringbootWebfluxDemo.controller;


import dinixweb.io.SpringbootWebfluxDemo.model.Products;
import dinixweb.io.SpringbootWebfluxDemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class ProductController {


    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(value = "/product", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Products> fetchAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "product/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Products> getSingleProduct(@PathVariable("id") String id) {
        return productService.getProductById(id);
    }

    @GetMapping(value = "/product-range", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Products> getProductByPriceRange(@RequestParam(value = "min", required = true) double min,
                                                 @RequestParam(value = "min", required = true) double max) {
        return productService.getProductByRange(min, max);
    }

    @PostMapping(value = "/addProduct")
    public Mono<Products> addProduct(@RequestBody Mono<Products> productsMono) {
        return productService.addProduct(productsMono);
    }

    @PutMapping(value = "/product/update/{id}")
    public Mono<Products> addProduct(@PathVariable("id") String id, @RequestBody Mono<Products> productsMono) {
        return productService.updateProduct(productsMono, id);
    }

    @DeleteMapping(value = "/product/{id}")
    public Mono<Void> deleteProduct(@PathVariable("id") String id) {
        return productService.deleteProductById(id);
    }
}
