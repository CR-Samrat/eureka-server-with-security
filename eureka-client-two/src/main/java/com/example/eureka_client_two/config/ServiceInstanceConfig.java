package com.example.eureka_client_two.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceInstanceConfig {

    @Autowired
    private DiscoveryClient discoveryClient;
    
    @Bean
    public ServiceInstance orderInstance(){
        return discoveryClient.getInstances("order-service").get(0);
    }
}
