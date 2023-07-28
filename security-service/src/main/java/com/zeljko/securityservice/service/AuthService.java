package com.zeljko.securityservice.service;

import com.zeljko.securityservice.entity.UserCredential;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthService {

    @Autowired
    private  UserCredentialRepository userCredentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveUser(UserCredential credential){
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        userCredentialRepository.save(credential);
        return "User added to the system";
    }


}
