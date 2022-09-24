package dinixweb.io.SpringbootWebfluxDemo.router;

import dinixweb.io.SpringbootWebfluxDemo.handler.ProductHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private ProductHandler productHandler;

    @Bean
    public RouterFunction<ServerResponse>routerFunction(){
        return RouterFunctions.route()
                .GET("/product", productHandler::getAllProducts)
                .GET("/product/{id}",productHandler::getProductById)
                .build();
    }
}
