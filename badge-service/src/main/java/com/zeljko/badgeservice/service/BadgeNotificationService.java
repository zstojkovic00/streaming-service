package com.zeljko.badgeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;


@Service
@RequiredArgsConstructor
public class BadgeNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendBadgeNotification(String userId, String message){
        messagingTemplate.convertAndSendToUser(userId, "/queue/badge-notifications", message);

    }


}
