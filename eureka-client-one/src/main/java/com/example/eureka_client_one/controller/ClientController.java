package com.example.eureka_client_one.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public String getWelcomeMessage(@RequestHeader("authorities") String authorities){

        if(authorities != null && (authorities.contains("ROLE_USER") || authorities.contains("ROLE_ADMIN"))){
            return "Welcome to Order service";
        }
        throw new RuntimeException("You are un authorize to use this service");
    }

    @GetMapping
    public List<OrderDetailsDto> getAllOrders(@RequestHeader("authorities") String authorities){

        if(authorities != null && authorities.contains("ROLE_ADMIN")){
            return this.clientService.getOrders();
        }
        throw new RuntimeException("You are un authorize to use this service");
    }

    @GetMapping("/status/{id}")
    public OrderDetailsDto getOrderById(@PathVariable("id") int id, @RequestHeader("authorities") String authorities){

        if(authorities != null && (authorities.contains("ROLE_USER") || authorities.contains("ROLE_ADMIN"))){
            return this.clientService.getOrderById(id);
        }
        throw new RuntimeException("You are un authorize to use this service");
    }

    @GetMapping("/send")
    public OrderDetailsDto giveOrderToRestaurant(@RequestHeader("authorities") String authorities){

        if(authorities != null && authorities.contains("ROLE_ADMIN")){
            return this.clientService.getRandomOrder();
        }
        throw new RuntimeException("You are un authorize to use this service");
    }
}
