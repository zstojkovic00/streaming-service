package com.zeljko.securityservice.handler;


import com.zeljko.securityservice.model.Provider;
import com.zeljko.securityservice.model.Role;
import com.zeljko.securityservice.model.UserCredential;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import com.zeljko.securityservice.service.AuthService;
import com.zeljko.securityservice.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final UserCredentialRepository repository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if ("github".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = principal.getAttributes();
            String email = attributes.getOrDefault("login", "").toString();
            String name = attributes.getOrDefault("name", "").toString();
            repository.findByEmail(email)
                    .ifPresentOrElse(user -> {
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(user.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    }, () -> {
                        UserCredential userCredential = new UserCredential();
                        userCredential.setRole(Role.USER);
                        userCredential.setEmail(email);
                        userCredential.setName(name);
                        userCredential.setProviderId(Provider.github.name());
                        repository.save(userCredential);
                        DefaultOAuth2User newUser = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority(userCredential.getRole().name())),
                                attributes, "id");
                        Authentication securityAuth = new OAuth2AuthenticationToken(newUser, List.of(new SimpleGrantedAuthority(userCredential.getRole().name())),
                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());
                        SecurityContextHolder.getContext().setAuthentication(securityAuth);
                    });
        }
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:3000/");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
