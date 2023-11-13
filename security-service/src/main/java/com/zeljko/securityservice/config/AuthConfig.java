package com.zeljko.securityservice.config;

import com.zeljko.securityservice.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.zeljko.securityservice.model.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfig  {

        private final AuthenticationFilter authenticationFilter;
        private final AuthenticationProvider authenticationProvider;

        // Bypass authentication for every request with pattern /auth/**
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/register", "auth/login")
                            .permitAll()
                            .requestMatchers(DELETE, "/**").hasAuthority(ADMIN.name())
                            .anyRequest().authenticated()
                    ).authenticationProvider(authenticationProvider)
                    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }



}
