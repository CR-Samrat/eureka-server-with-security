package com.example.eureka_feign_client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eureka_feign_client.client.OrderServiceClient;
import com.example.eureka_feign_client.dto.OrderDetailsDto;

@RestController
@RequestMapping("/feign")
public class ServiceController {

    @Autowired
    private OrderServiceClient orderServiceClient;
    
    @GetMapping("/welcome")
    public String getWelcomeMessage(){
        return "Welcome to feign client";
    }

    @GetMapping("/orders")
    public List<OrderDetailsDto> getOrders(){
        return this.orderServiceClient.getOrders();
    }

    @GetMapping("/order/{id}")
    public OrderDetailsDto getOrderById(@PathVariable("id") int id){
        return this.orderServiceClient.getOrderById(id);
    }
}
