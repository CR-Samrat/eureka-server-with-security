package com.example.api_gateway_service.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.example.api_gateway_service.service.JwtService;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtService jwtService;

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest request = null;

            if(validator.isSecured.test(exchange.getRequest())){

                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }

                try {
                    this.jwtService.validateToken(authHeader);

                    String authorities = String.join(",",this.jwtService.extractAuthoritiesFromToken(authHeader));

                    request = exchange.getRequest()
                            .mutate()
                            .header("authorities", authorities)
                            .build();

                } catch (Exception e) {
                    System.out.println("Invalid credentials");

                    throw new RuntimeException("Authorization fail");
                }
            }

            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config{

    }
}
