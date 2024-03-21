package com.zeljko.securityservice.service;

import com.zeljko.securityservice.dto.UserDTO;
import com.zeljko.securityservice.model.UserCredential;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserCredentialRepository repository;


    public Optional<UserDTO> getCurrentUserInfo(Principal principal) {
        return repository.findByEmail(principal.getName()).map(UserService::convert);
    }


    public static UserDTO convert(UserCredential u) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(u.getId());
        userDTO.setName(u.getName());
        userDTO.setEmail(u.getEmail());
        userDTO.setRole(u.getRole());
        return userDTO;
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
