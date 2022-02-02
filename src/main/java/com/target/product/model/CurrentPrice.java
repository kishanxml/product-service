package com.target.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class CurrentPrice{
    @Id
    @JsonIgnore
    public double p_id;
    public double value;
    public String currency_code;

    @Override
    public String toString() {
        return String.format("CurrentPrice[id=%s, value='%s', currency_code='%s']",p_id, value, currency_code);
    }

}