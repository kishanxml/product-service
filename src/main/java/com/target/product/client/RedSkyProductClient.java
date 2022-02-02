package com.target.product.client;

import com.target.product.exception.ServiceException;
import com.target.product.model.ProductNameResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class RedSkyProductClient {

    @Value("${RedSky.getProductName}")
    private String getProductNameUri;

    @Value("${RedSky.baseUri}")
    private String baseUri;

    public Mono<ProductNameResponse> getProductById(String id) {
        return WebClient.builder().baseUrl(baseUri).filters(exchangeFilterFunctions -> {
                    exchangeFilterFunctions.add(logRequest());
                    exchangeFilterFunctions.add(logResponse());
                }).build().get()
                .uri(getProductNameUri, id)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class)
                        .flatMap(body -> {
                            return Mono.error(new ServiceException(body, response.rawStatusCode()));
                        }))
                .bodyToMono(ProductNameResponse.class);
    }

    ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            System.out.println("Response Status Code: " + clientResponse.rawStatusCode());
            return Mono.just(clientResponse);
        });
    }
}
