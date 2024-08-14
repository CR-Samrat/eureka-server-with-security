package com.example.eureka_feign_client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private int orderId;
    private String name;
    private int quantity;
    private double price;
}
