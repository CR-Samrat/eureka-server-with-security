package com.example.eureka_client_two.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    private int id;
    private String name;
    private String address;
    private double rating;
    private int orderDelivered;
    private OrderDetailsDto order;
}
