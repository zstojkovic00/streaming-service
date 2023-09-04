package com.zeljko.securityservice.controller;

import com.zeljko.securityservice.dto.UserDTO;
import com.zeljko.securityservice.model.UserCredential;
import com.zeljko.securityservice.service.UserService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserCredential> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/current")
    public UserDTO getCurrentUser(Principal principal) {
        return userService.getCurrentUserInfo(principal).orElseThrow(NotFoundException::new);
    }

}
