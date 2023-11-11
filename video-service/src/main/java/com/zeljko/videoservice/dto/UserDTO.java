package com.zeljko.videoservice.dto;


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
    private Role role;
}