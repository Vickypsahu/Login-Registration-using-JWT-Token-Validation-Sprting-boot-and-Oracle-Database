package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    
    public User getEmail(String email);

    public User saveUser(User user);
}
