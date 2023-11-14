package com.zeljko.securityservice.service;

import com.zeljko.securityservice.model.UserCredential;
import com.zeljko.securityservice.model.oauth2.OAuth2UserDetails;
import com.zeljko.securityservice.respository.UserCredentialRepository;
import com.zeljko.securityservice.util.OAuth2UserDetailFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomOAuth2UserDetailService extends DefaultOAuth2UserService {

    private final UserCredentialRepository userCredentialRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {

        } catch (Exception e) {

        }

        return super.loadUser(userRequest);
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

        UserCredential userDetails;

        if (user.isPresent()) {
            userDetails = user.get();

            if (!userDetails.getProviderId().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
                throw new IllegalArgumentException("400: Invalid site login with" + userDetails.getProviderId());
            }

            // TODO:   updateOAuth2User();
        }
        {
            // TODO:    registerNewOAuth2();
        }

        return null;
    }


}