package com.example.authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authentication_service.dto.AuthRequest;
import com.example.authentication_service.model.UserCredentials;
import com.example.authentication_service.repository.UserCredentialsRepository;

@Service
public class UserService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserCredentials createUser(UserCredentials user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userCredentialsRepository.save(user);
    }

    public String authorizeUser(AuthRequest request){

        Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if(authenticate.isAuthenticated()){
            return this.jwtService.generateTokenForUser(request.getUsername(), authenticate.getAuthorities());
        }else{
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    public Boolean validateToken(String token){
        return this.jwtService.validateToken(token);
    }
}
