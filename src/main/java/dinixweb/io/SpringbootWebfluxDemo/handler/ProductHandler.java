package dinixweb.io.SpringbootWebfluxDemo.handler;

import dinixweb.io.SpringbootWebfluxDemo.model.Products;
import dinixweb.io.SpringbootWebfluxDemo.repository.ProductRepository;
import dinixweb.io.SpringbootWebfluxDemo.service.ProductService;
import dinixweb.io.SpringbootWebfluxDemo.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductHandler {


    @Autowired
    private ProductRepository productRepository;

    public Mono<ServerResponse> getAllProducts(ServerRequest request){
        Flux<Products> productsFlux =  productRepository.findAll().map(AppUtils::entityToModel);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productsFlux, Products.class);
    }

    public Mono<ServerResponse> getProductById(ServerRequest request){
        Mono<Products> productsMono = productRepository.findById(request.pathVariable("id")).map(AppUtils::entityToModel);
        return  ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productsMono, Products.class);
    }



}
