package com.example.eureka_client_two.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class ClientController {
    
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestClient.Builder restClientBuilder;

    @GetMapping("/welcome")
    public String welcomeMessage(){

        ServiceInstance serviceInstance = discoveryClient.getInstances("eureka-client-one").get(0);

        String response = restClientBuilder.build()
                                    .get()
                                    .uri(serviceInstance.getUri()+"/welcome")
                                    .retrieve()
                                    .body(String.class);

        return response;
    }
}
