package com.target.product.model;

import lombok.Data;

@Data
public class ProductPriceResponse{
    public String id;
    public String name;
    public CurrentPrice current_price;

    public ProductPriceResponse(String name, CurrentPrice currentPrice, String id) {
        this.id=id;
        this.name = name;
        this.current_price = currentPrice;
    }

}

