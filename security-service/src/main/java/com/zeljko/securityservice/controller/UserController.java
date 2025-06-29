package com.zeljko.securityservice.controller;

import com.zeljko.securityservice.controller.response.UserResponse;
import com.zeljko.securityservice.respository.model.UserCredential;
import com.zeljko.securityservice.service.UserService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserCredential> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/principal")
    public UserResponse getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userService.getPrincipal(name).orElseThrow(NotFoundException::new);
    }
}
