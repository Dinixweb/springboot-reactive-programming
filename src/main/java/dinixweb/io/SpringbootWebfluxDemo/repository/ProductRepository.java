package dinixweb.io.SpringbootWebfluxDemo.repository;

import dinixweb.io.SpringbootWebfluxDemo.entity.ProductEntity;
import dinixweb.io.SpringbootWebfluxDemo.model.Products;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {
    Flux<Products> findByPriceBetween(Range<Double> priceRange);
}
