package com.example.demo.controller;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.JwtResponse;
import com.example.demo.entity.User;
import com.example.demo.security.UserDetailsServiceImpl;
import com.example.demo.websecurity.JwtHelper;

@RestController
@RequestMapping("/api")
public class SigninController {


    @Autowired
    private UserDetailsServiceImpl detailsServiceImpl;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;



    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody User user)
    {
        this.doAuthenticate(user.getEmail(), user.getPassword());
        UserDetails userDetails=detailsServiceImpl.loadUserByUsername(user.getEmail());
         String token = this.helper.generateToken(userDetails);
         JwtResponse response = JwtResponse.builder()
         .jwttoken(token)
         .username(userDetails.getUsername()).build();
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

     private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}

    

    

