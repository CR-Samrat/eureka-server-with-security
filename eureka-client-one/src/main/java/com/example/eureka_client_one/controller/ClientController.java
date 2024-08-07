package com.example.eureka_client_one.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    
    @GetMapping("/welcome")
    public String getWelcomeMessage(){
        return "Welcome to Eureka client from Client one";
    }
}
