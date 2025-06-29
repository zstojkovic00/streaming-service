package com.zeljko.securityservice;

import com.zeljko.securityservice.controller.request.RegisterRequest;
import com.zeljko.securityservice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import static com.zeljko.securityservice.respository.model.Role.ADMIN;
import static com.zeljko.securityservice.respository.model.Role.USER;

@SpringBootApplication
@EnableDiscoveryClient
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(SecurityServiceApplication.class);

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthService service
    ) {
        return args -> {
            var zeljko = new RegisterRequest(
                    "Zeljko",
                    "00zeljkostojkovic@gmail.com",
                    "test1234",
                    ADMIN
            );

            var user = new RegisterRequest(
                    "User",
                    "user@gmail.com",
                    "test1234",
                    USER
            );

            log.info("Admin token: {}", service.register(zeljko).token());
            log.info("User token: {}", service.register(user).token());
        };
    }

}
