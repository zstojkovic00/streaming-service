package com.zeljko.securityservice.model.oauth2;

import java.util.Map;

public class OAuth2GithubUser extends OAuth2UserDetails{

    public OAuth2GithubUser(Map<String, Object> attributes){
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("login");
    }
}