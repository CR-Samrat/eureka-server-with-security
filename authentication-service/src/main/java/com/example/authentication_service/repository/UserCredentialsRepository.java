package com.example.authentication_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authentication_service.model.UserCredentials;
import java.util.Optional;


@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer>{
    Optional<UserCredentials> findByUsername(String username);
}
