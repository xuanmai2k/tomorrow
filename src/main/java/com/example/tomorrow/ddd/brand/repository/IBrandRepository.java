package com.example.tomorrow.ddd.brand.repository;

import com.example.tomorrow.ddd.brand.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBrandRepository extends MongoRepository<Brand,String> {
}
