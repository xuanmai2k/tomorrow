package com.example.tomorrow.ddd.bill.repository;

import com.example.tomorrow.ddd.bill.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillRepository extends MongoRepository <Bill, String> {
}
