package com.zeljko.badgeservice.service;

import com.zeljko.badgeservice.dto.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;


    public void sendNotification(String userId, String message){
        Notification notification = new Notification();
        notification.setMessage(message);
        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        notification.setTimestamp(date);
        String destination = "/topic/notification/user/" + userId;
        messagingTemplate.convertAndSend(destination, notification);

    }
}