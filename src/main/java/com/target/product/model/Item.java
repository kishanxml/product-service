package com.target.product.model;

import lombok.Data;

@Data
public class Item{
    public ProductDescription product_description;
    public Enrichment enrichment;
    public ProductClassification product_classification;
    public PrimaryBrand primary_brand;
}
