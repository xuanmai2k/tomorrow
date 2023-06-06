package com.example.tomorrow.ddd.user.repository;

import com.example.tomorrow.ddd.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository //khai báo liên kết vs collection trong db
public interface IUserRepository extends MongoRepository<User, String> {

}
