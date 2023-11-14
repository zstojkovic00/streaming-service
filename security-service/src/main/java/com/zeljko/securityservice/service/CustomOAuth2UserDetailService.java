package com.zeljko.securityservice.service;

import com.zeljko.securityservice.model.Role;
import com.zeljko.securityservice.model.UserCredential;
import com.zeljko.securityservice.model.oauth2.OAuth2UserDetailCustom;
import com.zeljko.securityservice.model.oauth2.OAuth2UserDetails;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import com.zeljko.securityservice.util.OAuth2UserDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserDetailService extends DefaultOAuth2UserService {

    private final UserCredentialRepository userCredentialRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {

            return checkingOAuth2User(userRequest, oAuth2User);

        } catch (AuthenticationException e) {
                throw e;
        } catch (Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }


    private OAuth2User checkingOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserDetails oAuth2UserDetails =
                OAuth2UserDetailFactory.
                        getOAuth2UserDetail(oAuth2UserRequest.getClientRegistration().getRegistrationId(),
                                oAuth2User.getAttributes());

        if (ObjectUtils.isEmpty(oAuth2UserDetails)) {
            throw new IllegalArgumentException("400: Can not find OAuth2 user from properties");
        }

        Optional<UserCredential> user = userCredentialRepository.findByNameAndProviderId(oAuth2UserDetails.getEmail(),
                oAuth2UserRequest.getClientRegistration().getRegistrationId());

        UserCredential userCredential;

        if (user.isPresent()) {
            userCredential = user.get();

            if (!userCredential.getProviderId().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
                throw new IllegalArgumentException("400: Invalid site login with" + userCredential.getProviderId());
            }

            userCredential = updateOAuth2UserDetail(userCredential, oAuth2UserDetails);
        } else {
            userCredential = registerNewOAuth2UserDetail(oAuth2UserRequest, oAuth2UserDetails);
        }

        return new OAuth2UserDetailCustom(userCredential.getId(),
                userCredential.getUsername(),
                userCredential.getPassword(),
                userCredential.getRole()

        );
    }

    public UserCredential registerNewOAuth2UserDetail(OAuth2UserRequest oAuth2UserRequest, OAuth2UserDetails oAuth2UserDetails) {
        UserCredential user = new UserCredential();
        user.setName(oAuth2UserDetails.getEmail());
        user.setProviderId(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        user.setRole(Role.USER);
        return userCredentialRepository.save(user);
    }


    public UserCredential updateOAuth2UserDetail(UserCredential user, OAuth2UserDetails oAuth2UserDetails) {
        user.setName(oAuth2UserDetails.getEmail());
        return userCredentialRepository.save(user);
    }


}