package com.zeljko.securityservice.service;

import com.zeljko.securityservice.respository.model.Provider;
import com.zeljko.securityservice.controller.request.AuthRequest;
import com.zeljko.securityservice.controller.response.AuthResponse;
import com.zeljko.securityservice.controller.request.RegisterRequest;
import com.zeljko.securityservice.respository.model.UserCredential;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserCredentialRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Transactional
    public AuthResponse register(RegisterRequest request) {
        var user = new UserCredential(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.role(),
                Provider.local.name()
        );

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = repository.findByEmail(request.email()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

}
