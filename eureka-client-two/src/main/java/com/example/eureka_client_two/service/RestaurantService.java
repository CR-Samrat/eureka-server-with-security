package com.example.eureka_client_two.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.example.eureka_client_two.dto.OrderDetailsDto;
import com.example.eureka_client_two.dto.Restaurant;

import jakarta.annotation.PostConstruct;

@Service
public class RestaurantService {
    
    private List<Restaurant> restaurants = null;

    private List<String> names = Arrays.asList("Mukhoruchi","Dada Boudi","Haji","Taj","Quality");
    private List<String> addresses = Arrays.asList("Krishnanagar","Barakpur","Kolkata","Mumbai","Krishnanagar");

    @PostConstruct
    public void LoadRestaurantData(){
        restaurants = IntStream.range(0, 5)
                                .mapToObj(i ->{
                                    return Restaurant.builder()
                                                    .id(i)
                                                    .name(names.get(i))
                                                    .address(addresses.get(i))
                                                    .rating(new Random().nextDouble(5))
                                                    .orderDelivered(0)
                                                    .order(null)
                                                    .build();
                                })
                                .toList();
    }

    public List<Restaurant> getRestaurants(){
        return restaurants;
    }

    public Restaurant getRestaurantById(int id){
        return restaurants.stream()
                        .filter( res -> id == res.getId())
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("Invalid id"));
    }

    public Restaurant getOrder(int restaurantId, OrderDetailsDto order){
        Restaurant restaurant = this.getRestaurantById(restaurantId);

        if(restaurant.getOrder() == null){
            restaurant.setOrder(order);
        }
        else{
            throw new RuntimeException("Order already exists");
        }

        return restaurant;
    }

    public Restaurant finishOrder(int id){
        Restaurant restaurant = this.getRestaurantById(id);

        if(restaurant.getOrder() != null){
            restaurant.setOrder(null);
            restaurant.setOrderDelivered(restaurant.getOrderDelivered()+1);
        }else{
            throw new RuntimeException("No order to finish");
        }

        return restaurant;
    }

    public Restaurant cancelOrder(int id){
        Restaurant restaurant = this.getRestaurantById(id);

        if(restaurant.getOrder() != null){
            restaurant.setOrder(null);
        }else{
            throw new RuntimeException("No order to cancel");
        }

        return restaurant;
    }
}
