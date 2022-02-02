package com.target.product.controller;

import com.target.product.handler.ProductAndPriceHandler;
import com.target.product.model.CurrentPrice;
import com.target.product.model.ProductPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductAndPriceHandler productAndPriceHandler;

    @GetMapping("/{id}")
    private Mono<ProductPriceResponse> getProductAndPriceById(@PathVariable String id) {
        return productAndPriceHandler.getProductAndPriceDetails(id);
    }

    @PutMapping("/{id}")
    private Mono<CurrentPrice> putProductPrice(@PathVariable Double id, @RequestBody CurrentPrice currentPrice) {
       return productAndPriceHandler.saveProductPrice(currentPrice,id);
    }
}
