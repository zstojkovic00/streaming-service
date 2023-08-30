package com.zeljko.badgeservice.controller;

import com.zeljko.badgeservice.service.BadgeNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BadgeNotificationController {

    private final BadgeNotificationService badgeNotificationService;

    @MessageMapping("/badge-notifications")
    public void sendBadgeNotification(String userId, String message){
        badgeNotificationService.sendBadgeNotification(userId, message);
    }
}
