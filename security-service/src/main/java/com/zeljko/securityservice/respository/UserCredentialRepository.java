package com.zeljko.securityservice.respository;

import com.zeljko.securityservice.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

}
