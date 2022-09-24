package dinixweb.io.SpringbootWebfluxDemo.service;

import dinixweb.io.SpringbootWebfluxDemo.model.Products;
import dinixweb.io.SpringbootWebfluxDemo.repository.ProductRepository;
import dinixweb.io.SpringbootWebfluxDemo.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
public class ProductService {

    @Autowired
    private  final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Products> getAllProducts(){
        return productRepository.findAll().map(AppUtils::entityToModel);
    }

    public Mono<Products> getProductById(String id){
        return productRepository.findById(id).map(AppUtils::entityToModel);
    }

    public Flux<Products> getProductByRange(double min, double max){
        return productRepository.findByPriceBetween(Range.closed(min, max));
    }

    public Mono<Products> addProduct(Mono<Products> productsMono){
       return productsMono.map(AppUtils::modelToEntity)
                .flatMap(productRepository::insert)
                .map(AppUtils::entityToModel);
    }
    public Mono<Products> updateProduct(Mono<Products> productsMono, String id){
       return productRepository.findById(id)
                .flatMap(product->productsMono.map(AppUtils::modelToEntity)
                .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(AppUtils::entityToModel);
    }

    public Mono<Void> deleteProductById(String id){
        return productRepository.deleteById(id);
    }
}
