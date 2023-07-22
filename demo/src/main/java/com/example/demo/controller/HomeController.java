package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
public class HomeController
{
    @Autowired
    PasswordEncoder encoder;


    @Autowired
    private UserService userService;

     @PostMapping("api/register")
    public String getRegister(@RequestBody User user)
    {
        User u=null;
        u=userService.getEmail(user.getEmail());
        if(u==null)
        {
            user.setPassword(encoder.encode(user.getPassword()));
             User user1=userService.saveUser(user);
            if(user1!=null)
            {
                return "Registered successfully";
            }
            else{
                return "Something went wrong";
            }
        }
        else
        {
           return "User already exist!";
        }
    }

    @GetMapping("/test")
    public String Test()
    {
        return "Welcome";
    }

   
}

  



