package com.zeljko.securityservice.service;

import com.zeljko.securityservice.model.UserCredential;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserCredentialRepository repository;


    public UserCredential getCurrentUserInfo(Principal principal) {
        UserCredential user = repository.findByEmail(principal.getName()).get();
        return user;
    }

    public List<UserCredential> getAllUsers() {
        List<UserCredential> users = new ArrayList<>();
        try {
            repository.findAll().forEach(user -> users.add(user));
        } catch (Exception e) {
            log.error("An error occurred while fetching users: {}", e.getMessage());
        }
        return users;
    }

}
