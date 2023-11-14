package com.zeljko.securityservice.config;

import com.zeljko.securityservice.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class AuthConfig  {

        private final AuthenticationFilter authenticationFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/register", "auth/login")
                            .permitAll()
                            .anyRequest().authenticated()
                    )
                    .oauth2Login(Customizer.withDefaults())
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }



}
