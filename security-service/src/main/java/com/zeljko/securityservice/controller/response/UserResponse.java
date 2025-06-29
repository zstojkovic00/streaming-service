package com.zeljko.securityservice.controller.response;

import com.zeljko.securityservice.respository.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public record UserResponse(int id,
                           String name,
                           String email,
                           @Enumerated(EnumType.STRING) Role role) {}
