package com.example.tomorrow.ddd.user.controller;

import com.example.tomorrow.ddd.user.User;
import com.example.tomorrow.ddd.user.application.UserApplication;
import com.example.tomorrow.ddd.user.command.CommandUser;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;//kiểm tra giá trị null hay ko

@RestController
@RequestMapping(path="/api/user")
public class UserController {
    @Autowired
    private UserApplication userApplication;
    @PostMapping("/create")
    public Optional<User> create (HttpServletRequest httpServletRequest, @RequestBody CommandUser commandUser){
        try{
            User result = userApplication.create(commandUser);
            return Optional.of(result);
        } catch(Exception e){
            return Optional.empty();
        }
    }
    @GetMapping("/get/{id}")
    public Optional<User> getById(HttpServletRequest httpServletRequest, @PathVariable ObjectId id){ //lấy giá trị từ http
        try{
            User result = userApplication.getById(id);
            return Optional.of(result);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/delete/{id}")
    public Optional<String> delete (HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try {
            Boolean is_delete = userApplication.delete(id);
            if(is_delete){
                return Optional.of("success");
            }else{
                return Optional.of("fail");
            }
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/getAll")
    public Optional<List<User>> getAll (HttpServletRequest httpServletRequest){
        try {
            List<User> result = userApplication.getAll();
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @PutMapping("/update/{id}")
    public Optional<User> update(HttpServletRequest httpServletRequest,@RequestBody CommandUser commandUser, @PathVariable ObjectId id){
        try {
            User result = userApplication.update(commandUser, id.toHexString());
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

}
