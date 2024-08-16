package com.example.eureka_client_two.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.example.eureka_client_two.dto.OrderDetailsDto;
import com.example.eureka_client_two.dto.Restaurant;
import com.example.eureka_client_two.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
public class ClientController {

    @Autowired
    private RestClient.Builder restClientBuilder;

    @Autowired
    private ServiceInstance orderInstance;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/welcome")
    public String welcomeMessage(){

        return "Welcome to Restaurant service";
    }

    @GetMapping
    public List<Restaurant> getAllRestaurants(){
        return this.restaurantService.getRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable("id") int id){
        return this.restaurantService.getRestaurantById(id);
    }

    @GetMapping("/{id}/order/get")
    public Restaurant getOrder(@PathVariable("id") int id){

        OrderDetailsDto response = restClientBuilder.build()
                                    .get()
                                    .uri(orderInstance.getUri()+"/orders/send")
                                    .retrieve()
                                    .body(OrderDetailsDto.class);

        return this.restaurantService.getOrder(id, response);
    }

    @PostMapping("/{id}/order/done")
    public Restaurant finishOrder(@PathVariable("id") int id){
        return this.restaurantService.finishOrder(id);
    }

    @PostMapping("/{id}/order/cancel")
    public Restaurant cancelOrder(@PathVariable("id") int id){
        return this.restaurantService.cancelOrder(id);
    }

    @GetMapping("/order-details/{id}")
    public OrderDetailsDto getOrderDetails(@PathVariable("id") int orderId){

        OrderDetailsDto response = restClientBuilder.build()
                                    .get()
                                    .uri(orderInstance.getUri()+"/orders/status/"+orderId)
                                    .retrieve()
                                    .body(OrderDetailsDto.class);

        return response;
    }

}
