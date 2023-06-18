package com.example.tomorrow.ddd.auth.controller;

import com.example.tomorrow.ddd.auth.application.AuthApplication;
import com.example.tomorrow.ddd.auth.command.CommandLogin;
import com.example.tomorrow.ddd.auth.command.CommandRegister;
import com.example.tomorrow.ddd.auth.model.Account;
import com.example.tomorrow.ddd.user.User;
import com.example.tomorrow.ddd.user.application.UserApplication;
import com.example.tomorrow.ddd.user.command.CommandChangePassword;
import com.example.tomorrow.jwt.JWTUtility;
import com.example.tomorrow.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/auth")
public class AuthController {
    @Autowired
    private AuthApplication authApplication;
    @Autowired
    private UserApplication userApplication;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private  JWTUtility jwtUtility;
    @PostMapping("/register")
    public Optional<ResponseObject> register(@RequestBody CommandRegister commandRegister){
        try {
            if(commandRegister == null){
                throw new Exception("Missing Params");
            }
            Account register = authApplication.register(commandRegister);
            ResponseObject res = ResponseObject.builder().status(9999).message("success").payload("create_account_successfully").build();
            return Optional.of(res);
        } catch (Exception e){
            ResponseObject res = ResponseObject.builder().status(-9999).message("failed").payload(e.getMessage()).build();
            return Optional.of(res);
        }
    }
    @PostMapping("/create")
    public Optional<ResponseObject> register(@RequestBody Account account) {
        try {
            if (account == null) {
                throw new Exception("Missing Params");
            }
            authApplication.create(account);
            ResponseObject res = ResponseObject.builder().status(9999).message("success").payload("create_account_successfully").build();
            return Optional.of(res);
        } catch (Exception e) {
            ResponseObject res = ResponseObject.builder().status(-9999).message("failed").build();
            return Optional.of(res);
        }
    }
    @PostMapping("/login")
    public ResponseObject authenticateUser(@Valid @RequestBody CommandLogin commandLogin){
        try {
            try {

                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                commandLogin.getPhone(),
                                commandLogin.getPassword()
                        )
                );
            }catch (Exception e){
                throw new Exception("invalid credentials");
            }
            final Account userDetails = authApplication.findByPhoneNumber(commandLogin.getPhone());


            final String token = jwtUtility.generateToken(userDetails);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseObject.builder().status(9999).message("success").payload(response).build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            return ResponseObject.builder().status(-9999).message("failed").build();
        }
    }

    @PostMapping("/forget_password")
    public Optional<ResponseObject> forgetPassword(@RequestBody CommandChangePassword commandChangePassword) {
        try {
            if (commandChangePassword == null) {
                throw new Exception("Missing Params");
            }
            Boolean validated = authApplication.forgetPassword(commandChangePassword);
            ResponseObject res = ResponseObject.builder().status(9999).message("success").payload(validated).build();
            return Optional.of(res);
        } catch (Exception e) {
            ResponseObject res = ResponseObject.builder().status(-9999).message("failed").build();
            return Optional.of(res);
        }
    }

    @PostMapping("/change_password")
    public Optional<ResponseObject> changePassword(@RequestBody CommandChangePassword commandChangePassword) {
        try {
            if (commandChangePassword == null) {
                throw new Exception("Missing Params");
            }
            Boolean validated = authApplication.changePassword(commandChangePassword);
            ResponseObject res = ResponseObject.builder().status(9999).message("success").payload(validated).build();
            return Optional.of(res);
        } catch (Exception e) {
            ResponseObject res = ResponseObject.builder().status(-9999).message("failed").build();
            return Optional.of(res);
        }
    }

    @GetMapping("/get_user/{phone}")
    public Optional<ResponseObject> getUserByPhoneNumber(@PathVariable String phone) {
        try {
            User user  = authApplication.getUserByPhoneNumber(phone);
            ResponseObject res = ResponseObject.builder().status(9999).message("success").payload(user).build();
            return Optional.of(res);
        } catch (Exception e) {
            ResponseObject res = ResponseObject.builder().status(-9999).message("failed").build();
            return Optional.of(res);
        }
    }

}
