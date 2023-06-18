package com.example.tomorrow.service;

import com.example.tomorrow.ddd.auth.application.AuthApplication;
import com.example.tomorrow.ddd.auth.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AuthApplication authApplication;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        //find by phone number
        Account account = authApplication.findByPhoneNumber(phone);
        if (account == null) {
            throw new UsernameNotFoundException("not found");
        }
        return new User(account.getPhone(), account.getPassword(), new ArrayList<>());
    }
}
