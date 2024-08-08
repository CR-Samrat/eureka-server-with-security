package com.example.eureka_client_one.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.eureka_client_one.dto.OrderDetailsDto;

import jakarta.annotation.PostConstruct;

@Service
public class ClientService {
    
    private List<OrderDetailsDto> orders;

    private List<String> names = Arrays.asList("Pizza","Biryani","Fried Rice","Egg Roll","Chicken Kabab");
    private List<Double> prices = Arrays.asList(250.0, 150.0, 100.0, 35.0, 210.0);

    @PostConstruct
    public void loadData(){
        this.orders = IntStream.range(0, 5)
                            .mapToObj( i -> {
                                String orderName = names.get(i);
                                int orderQuantity = 1 + new Random().nextInt(5);
                                double orderPrice = prices.get(i)*orderQuantity;

                                return OrderDetailsDto.builder()
                                                        .orderId(i+1)
                                                        .name(orderName)
                                                        .quantity(orderQuantity)
                                                        .price(orderPrice)
                                                        .build();
                            })
                            .toList();
    }

    public List<OrderDetailsDto> getOrders(){
        return this.orders;
    }

    public OrderDetailsDto getOrderById(int id){
        OrderDetailsDto order = this.orders.stream()
                                            .filter(ord -> ord.getOrderId() == id)
                                            .findAny()
                                            .orElseThrow(() -> new RuntimeException("Invalid id"));

        return order;
    }
}
