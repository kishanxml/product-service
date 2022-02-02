package com.target.product;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	public WebClient createWebClient() throws SSLException {
		SslContext sslContext = SslContextBuilder
				.forClient()
				.trustManager(InsecureTrustManagerFactory.INSTANCE)
				.build();
		HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
		return WebClient.builder().baseUrl("https://redsky-uat.perf.target.com").filters(exchangeFilterFunctions -> {
			exchangeFilterFunctions.add(logRequest());
			exchangeFilterFunctions.add(logResponse());
		}).clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}


	ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			StringBuilder sb = new StringBuilder("Request: \n"+ clientRequest.url());
			System.out.println(sb);
			return Mono.just(clientRequest);
		});
	}
	ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientRequest -> {
			StringBuilder sb = new StringBuilder("Resposne: \n"+ clientRequest.rawStatusCode());
			System.out.println(sb);
			return Mono.just(clientRequest);
		});
	}
}
