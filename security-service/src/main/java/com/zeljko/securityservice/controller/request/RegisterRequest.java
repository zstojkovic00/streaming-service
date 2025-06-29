package com.zeljko.securityservice.controller.request;

import com.zeljko.securityservice.respository.model.Role;


public record RegisterRequest(String username, String email, String password, Role role) {}