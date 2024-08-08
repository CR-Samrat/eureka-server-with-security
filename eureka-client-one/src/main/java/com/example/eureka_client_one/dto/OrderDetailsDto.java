package com.example.eureka_client_one.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsDto {
    private int orderId;
    private String name;
    private int quantity;
    private double price;
}
