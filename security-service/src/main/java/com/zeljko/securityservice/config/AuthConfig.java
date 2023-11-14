package com.zeljko.securityservice.config;

import com.zeljko.securityservice.filter.AuthenticationFilter;
import com.zeljko.securityservice.handler.OAuth2FailureHandler;
import com.zeljko.securityservice.handler.OAuth2SuccessHandler;
import com.zeljko.securityservice.service.CustomOAuth2UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class AuthConfig {

    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomOAuth2UserDetailService customOAuth2UserDetailService;

    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "auth/login")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 ->
                        oauth2.loginPage("/login")
                                .defaultSuccessUrl("/home/index", true)
                                .userInfoEndpoint(userInfo -> {
                                    userInfo.userService(customOAuth2UserDetailService);
                                }).successHandler(oAuth2SuccessHandler)
                                .failureHandler(oAuth2FailureHandler)
                ).sessionManagement( s ->{
                    s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
