package com.example.tomorrow.ddd.product.repository;

import com.example.tomorrow.ddd.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends MongoRepository<Product, String> {
}
