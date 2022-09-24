package dinixweb.io.SpringbootWebfluxDemo;

import dinixweb.io.SpringbootWebfluxDemo.controller.ProductController;
import dinixweb.io.SpringbootWebfluxDemo.handler.ProductHandler;
import dinixweb.io.SpringbootWebfluxDemo.model.Products;
import dinixweb.io.SpringbootWebfluxDemo.repository.ProductRepository;
import dinixweb.io.SpringbootWebfluxDemo.router.RouterConfig;
import dinixweb.io.SpringbootWebfluxDemo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class SpringbootWebfluxDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService; // use this for api base approach

	@MockBean
	private ProductRepository productRepository; // use this if you are functional endpoint

	@Test
	public void addProductTest(){
		Mono<Products> productsMono = Mono.just(new Products("10", "Tecno Mobile", 10, 45000));
		when(productService.addProduct(productsMono)).thenReturn(productsMono);

		webTestClient.post().uri("/addProduct").body(Mono.just(productsMono), Products.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void productsListTest(){
		Flux<Products> productsFlux = Flux.just(new Products("22", "Infinix Z12", 2, 105000),
				new Products("23", "Beat By Dre Bluetooth Headphones",5,57000));
		when(productService.getAllProducts()).thenReturn(productsFlux);
		Flux<Products> productsFlux1  = webTestClient.get().uri("/product")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(Products.class)
				.getResponseBody();

		StepVerifier.create(productsFlux1)
				.expectSubscription()
				.expectNext(new Products("22", "Infinix Z12", 2, 105000))
				.expectNext(new Products("23", "Beat By Dre Bluetooth Headphones",5,57000))
				.verifyComplete();
	}

	@Test
	public void getProductById(){
		Mono<Products> productsMono = Mono.just(new Products("22", "Infinix Z12", 2, 105000));
		when(productService.getProductById(any())).thenReturn(productsMono);

		Flux<Products> productsFlux1  = webTestClient.get().uri("/product/22")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(Products.class)
				.getResponseBody();

		StepVerifier.create(productsFlux1)
				.expectSubscription()
				.expectNextMatches(e->e.getName().equals("Infinix Z12"))
				.verifyComplete();

	}

	@Test
	public void updateProduct(){
		Mono<Products> productsMono = Mono.just(new Products("22", "Infinix Z12", 2, 105000));
		when(productService.updateProduct(productsMono, "22")).thenReturn(productsMono);

		webTestClient.put().uri("/product/update/22").body(Mono.just(productsMono), Products.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void deleteProduct(){
		given(productService.deleteProductById(any())).willReturn(Mono.empty());

		webTestClient.delete().uri("/product/22")
				.exchange()
				.expectStatus()
				.isOk();
	}


}
