package com.zeljko.badgeservice.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String message;
    private Date timestamp;
}