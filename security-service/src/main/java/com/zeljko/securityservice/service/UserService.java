package com.zeljko.securityservice.service;

import com.zeljko.securityservice.controller.response.UserResponse;
import com.zeljko.securityservice.respository.model.UserCredential;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserCredentialRepository repository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserCredentialRepository repository) {
        this.repository = repository;
    }

    public Optional<UserResponse> getPrincipal(String name) {
        return repository.findByEmail(name).map(UserService::convert);
    }


    public static UserResponse convert(UserCredential u) {
        return new UserResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getRole()
        );

    }

    public List<UserCredential> getAllUsers() {
        List<UserCredential> users = new ArrayList<>();
        try {
            users.addAll(repository.findAll());
        } catch (Exception e) {
            log.error("An error occurred while fetching users: {}", e.getMessage());
        }
        return users;
    }

}
