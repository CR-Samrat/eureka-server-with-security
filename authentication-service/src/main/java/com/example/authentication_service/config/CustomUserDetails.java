package com.example.authentication_service.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.authentication_service.model.UserCredentials;

public class CustomUserDetails implements UserDetails{

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(UserCredentials user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = Arrays.stream(user.getAuthorities().split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
}
