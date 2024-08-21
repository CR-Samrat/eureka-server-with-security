package com.example.authentication_service.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.authentication_service.model.UserCredentials;
import com.example.authentication_service.repository.UserCredentialsRepository;

public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> user = this.userCredentialsRepository.findByUsername(username);

        return user.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }
    
}
