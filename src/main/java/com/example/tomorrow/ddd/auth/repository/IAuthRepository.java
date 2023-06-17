package com.example.tomorrow.ddd.auth.repository;

import com.example.tomorrow.ddd.auth.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAuthRepository extends MongoRepository<Account,String> {
}
