package com.example.eureka_client_two.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.example.eureka_client_two.dto.OrderDetailsDto;

@RestController
@RequestMapping("/restaurant")
public class ClientController {

    @Autowired
    private RestClient.Builder restClientBuilder;

    @Autowired
    private ServiceInstance orderInstance;

    @GetMapping("/welcome")
    public String welcomeMessage(){

        return "Welcome to Restaurant service";
    }

    @GetMapping("/order-details/{id}")
    public OrderDetailsDto getOrderDetails(@PathVariable("id") int id){

        OrderDetailsDto response = restClientBuilder.build()
                                    .get()
                                    .uri(orderInstance.getUri()+"/orders/status/"+id)
                                    .retrieve()
                                    .body(OrderDetailsDto.class);

        return response;
    }

}
