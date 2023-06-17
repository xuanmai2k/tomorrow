package com.example.tomorrow.ddd.auth.application;

import com.example.tomorrow.ddd.auth.command.CommandRegister;
import com.example.tomorrow.ddd.auth.model.Account;
import com.example.tomorrow.ddd.auth.repository.IAuthRepository;
import com.example.tomorrow.ddd.user.User;
import com.example.tomorrow.ddd.user.application.UserApplication;
import com.example.tomorrow.ddd.user.command.CommandUser;
import io.micrometer.common.util.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthApplication implements UserDetailsService {
    @Autowired
    private UserApplication userApplication;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IAuthRepository iAuthRepository;

    public Account register(CommandRegister commandRegister) throws Exception{
        if(StringUtils.isBlank(commandRegister.getUserName())||StringUtils.isBlank(commandRegister.getPassword())||StringUtils.isBlank(commandRegister.getPhone())){
            throw new Exception("Missing Params");
        }
        if(this.count(commandRegister.getPhone()) > 0L){
            throw new Exception("Phone Exist");
        }
        CommandUser newUser = CommandUser.builder()
                .name(commandRegister.getName())
                .userName(commandRegister.getUserName())
                .password(commandRegister.getPassword())
                .email(commandRegister.getEmail())
                .phone(commandRegister.getPhone())
                .address(commandRegister.getAddress())
                .role(1) //user
                .build();
        User user = userApplication.create(newUser);
        if(user == null){
            return null;
        }
        Account account =Account.builder()
                .userName(newUser.getUserName())
                .password(newUser.getPassword())
                .name(newUser.getName())
                .phone(newUser.getPhone())
                .email(newUser.getEmail())
                .address(newUser.getAddress())
                .role("admin") // just admin?
                .status("active")
                .build();
        return this.create(account);
    }

    private Long count(String phone) { // xem đã có số này chưa
        Query query = new Query();
        query.addCriteria(Criteria.where("phone").is(phone));
        Long count = mongoTemplate.count(query, Account.class);
        return count;
    }

    public Account create(Account account){
        Long current_time = System.currentTimeMillis();
        account.setIsDelete(false);
        account.setCreatedAt(current_time);
        iAuthRepository.save(account); // why? insert?
        return account;
    }

    public Account findByPhoneNumber(String phone) {
        Query query = new Query();
        query.addCriteria(Criteria.where("phone").is(phone));
        return mongoTemplate.findOne(query, Account.class);
    }

    public Account getById (ObjectId id) {
        Account user = mongoTemplate.findById(id, Account.class);
        if(user != null) {
            if (user.getIsDelete()) {
                return null;
            }
            return user;
        } else return null;
    }





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
