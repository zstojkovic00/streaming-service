package com.zeljko.securityservice.dto;

import com.zeljko.securityservice.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private int id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
}
