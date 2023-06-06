package com.example.tomorrow.ddd.category.repository;

import com.example.tomorrow.ddd.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends MongoRepository <Category, String> {
}
