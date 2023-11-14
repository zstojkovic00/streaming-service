package com.zeljko.securityservice.respository;

import com.zeljko.securityservice.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential,Integer> {

    Optional<UserCredential> findByEmail(String email);
}
