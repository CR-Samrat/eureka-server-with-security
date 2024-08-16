package com.example.eureka_feign_client.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.eureka_feign_client.dto.OrderDetailsDto;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
    
    @GetMapping("/orders")
    List<OrderDetailsDto> getOrders();

    @GetMapping("/orders/status/{id}")
    OrderDetailsDto getOrderById(@PathVariable("id") int id);
}
