package com.example.eureka_client_one.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eureka_client_one.dto.OrderDetailsDto;
import com.example.eureka_client_one.service.ClientService;

@RestController
@RequestMapping("/orders")
public class ClientController {

    @Autowired
    private ClientService clientService;
    
    @GetMapping("/welcome")
    public String getWelcomeMessage(){
        return "Welcome to Order service";
    }

    @GetMapping
    public List<OrderDetailsDto> getAllOrders(){
        return this.clientService.getOrders();
    }

    @GetMapping("/status/{id}")
    public OrderDetailsDto getOrderById(@PathVariable("id") int id){
        return this.clientService.getOrderById(id);
    }
}
