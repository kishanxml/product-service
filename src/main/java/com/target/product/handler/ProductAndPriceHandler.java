package com.target.product.handler;

import com.target.product.client.RedSkyProductClient;
import com.target.product.exception.ServiceException;
import com.target.product.model.CurrentPrice;
import com.target.product.model.ProductPriceResponse;
import com.target.product.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProductAndPriceHandler {

    @Autowired
    RedSkyProductClient redSkyProductClient;

    @Autowired
    PriceRepository priceRepository;

    public Mono<ProductPriceResponse> getProductAndPriceDetails(String id) {
        Mono<String> user = redSkyProductClient.getProductById(id).map(productNameResponse -> productNameResponse.getData().getProduct().getItem().getProduct_classification().getProduct_type_name());
        Mono<CurrentPrice> item = priceRepository.findById(Double.valueOf(id)).map(currentPrice -> currentPrice);
        return user.zipWith(item).map(o -> { return new ProductPriceResponse(o.getT1(),o.getT2(),id);});
    }

    public Mono<CurrentPrice> saveProductPrice(CurrentPrice currentPrice, double id) {
        currentPrice.setP_id(id);
        return priceRepository.save(currentPrice);
    }
}
