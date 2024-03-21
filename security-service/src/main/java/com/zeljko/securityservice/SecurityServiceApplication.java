package com.zeljko.securityservice;

import com.zeljko.securityservice.request.RegisterRequest;
import com.zeljko.securityservice.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import static com.zeljko.securityservice.model.Role.ADMIN;
import static com.zeljko.securityservice.model.Role.USER;

@SpringBootApplication
@EnableDiscoveryClient
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthService service
    ) {
        return args -> {
            var zeljko = RegisterRequest.builder()
                    .username("Zeljko")
                    .email("zeljko@gmail.com")
                    .password("test1234")
                    .role(ADMIN)
                    .build();

            var user = RegisterRequest.builder()
                    .username("User")
                    .email("user@gmail.com")
                    .password("test1234")
                    .role(USER)
                    .build();

            System.out.println("Admin token " + service.register(zeljko).getToken());
            System.out.println("User token " + service.register(user).getToken());
        };
    }

}
