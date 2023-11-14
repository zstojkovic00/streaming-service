package com.zeljko.securityservice.util;

import com.thoughtworks.xstream.core.BaseException;
import com.zeljko.securityservice.model.Provider;
import com.zeljko.securityservice.model.oauth2.OAuth2GithubUser;
import com.zeljko.securityservice.model.oauth2.OAuth2UserDetails;

import java.util.Map;

public class OAuth2UserDetailFactory {

    public static OAuth2UserDetails getOAuth2UserDetail(String registrationId, Map<String, Object> attributes){
        if(registrationId.equals(Provider.github.name())){
            return new OAuth2GithubUser(attributes);
        } else {
            throw new IllegalArgumentException("Login with " + registrationId + " is not supported");
        }
    }
}