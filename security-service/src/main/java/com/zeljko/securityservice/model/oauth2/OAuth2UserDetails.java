package com.zeljko.securityservice.model.oauth2;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public abstract class OAuth2UserDetails {

    protected Map<String, Object> attributes;

    public abstract String getName();
    public abstract String getEmail();
}