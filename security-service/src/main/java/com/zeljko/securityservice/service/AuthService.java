package com.zeljko.securityservice.service;

import com.zeljko.securityservice.entity.UserCredential;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String saveUser(UserCredential credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        userCredentialRepository.save(credential);
        return "User added to the system";
    }
    public String generateToken(String username){
        return jwtService.generateToken(username);
    }
    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
