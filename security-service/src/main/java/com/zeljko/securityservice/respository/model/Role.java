package com.zeljko.securityservice.respository.model;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zeljko.securityservice.respository.model.Permission.*;


public enum Role {

    USER(Set.of(
            USER_READ,
            USER_UPDATE,
            USER_CREATE,
            USER_DELETE
    )),
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE
    ));

    private final Set<Permission> permission;

    public Set<Permission> getPermission() {
        return permission;
    }

    Role(Set<Permission> permission) {
        this.permission = permission;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){

        var authorities = getPermission()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
