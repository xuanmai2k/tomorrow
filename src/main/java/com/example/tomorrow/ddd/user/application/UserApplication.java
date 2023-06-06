package com.example.tomorrow.ddd.user.application;

import com.example.tomorrow.ddd.user.User;
import com.example.tomorrow.ddd.user.command.CommandUser;
import com.example.tomorrow.ddd.user.repository.IUserRepository;
import io.micrometer.common.util.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component //phải là Component để controller gọi
public class UserApplication {
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private MongoTemplate mongoTemplate; //thư viện làm này làm kia vs mongo

    public User create(CommandUser commandUser) throws Exception {
        if (StringUtils.isBlank(commandUser.getUserName()) || StringUtils.isBlank(commandUser.getPassword()) || StringUtils.isBlank(commandUser.getName()) || StringUtils.isBlank(commandUser.getEmail())) {
            throw new Exception("missing params");
        }
        User user = User.builder()
                .name(commandUser.getName())
                .userName(commandUser.getUserName())
                .password(commandUser.getPassword())
                .email(commandUser.getEmail())
                .phone(commandUser.getPhone())
                .address(commandUser.getAddress())
                .createdAt(System.currentTimeMillis()) // add time
                .build();
        return iUserRepository.insert(user);// insert vào database
    }

    public User getById(ObjectId id) {
        User user = mongoTemplate.findById(id, User.class);
        if (user != null) {
            if (user.getIsDelete()) {
                return null;
            }
            return user;
        } else {
            return null;
        }
    }

    public Boolean delete(ObjectId id) {
        Long current_time = System.currentTimeMillis();
        User user = mongoTemplate.findById(id, User.class);
        if (user != null) {
            user.setIsDelete(true);
            user.setCreatedAt(current_time);
            mongoTemplate.save(user, "user");
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAll() {
        List<User> users = mongoTemplate.findAll(User.class);
        List<User> validUsers = new ArrayList<>();
        for (User item : users) {
            if (!item.getIsDelete()) {
                validUsers.add(item);
            }
        }
        return validUsers;
    }

    public User update(CommandUser commandUser, String id) throws Exception {
        Long current_time = System.currentTimeMillis();
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        query.addCriteria(Criteria.where("isDelete").is(false));
        User updateUser = mongoTemplate.findOne(query, User.class);
        if (updateUser != null) {
            if (StringUtils.isBlank(commandUser.getUserName()) || StringUtils.isBlank(commandUser.getPassword()) || StringUtils.isBlank(commandUser.getName()) || StringUtils.isBlank(commandUser.getEmail())) {
                throw new Exception("missing params");
            }
            updateUser.setName(commandUser.getName());
            updateUser.setUserName(commandUser.getUserName());
            updateUser.setPassword(commandUser.getPassword());
            updateUser.setEmail(commandUser.getEmail());
            updateUser.setPhone(commandUser.getPhone());
            updateUser.setAddress(commandUser.getAddress());
            updateUser.setCreatedAt(current_time);
            return mongoTemplate.save(updateUser,"user");
        } else {
            return null;
        }
    }
}
