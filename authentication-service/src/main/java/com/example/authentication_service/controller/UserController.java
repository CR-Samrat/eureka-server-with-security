package com.example.authentication_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.authentication_service.dto.AuthRequest;
import com.example.authentication_service.model.UserCredentials;
import com.example.authentication_service.service.JwtService;
import com.example.authentication_service.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/create")
    public UserCredentials registerUser(@RequestBody UserCredentials newUser){
        return this.userService.createUser(newUser);
    }

    @GetMapping("/login")
    public String loginUser(@RequestBody AuthRequest loginRequest){
        return this.userService.authorizeUser(loginRequest);
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestParam("token") String token){
        Boolean isValid = this.userService.validateToken(token);
        return isValid;
    }

    @GetMapping("/info")
    public List<String> getTokenInfo(@RequestParam("token") String token){
        return this.jwtService.extractAuthoritiesFromToken(token);
    }
}
