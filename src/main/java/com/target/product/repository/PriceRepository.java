package com.target.product.repository;

import com.target.product.model.CurrentPrice;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends ReactiveCrudRepository<CurrentPrice, Double> {
}