package com.zeljko.badgeservice.service;

import com.zeljko.badgeservice.dto.VideoProgressMessage;
import com.zeljko.badgeservice.exception.BadgeNotFoundException;
import com.zeljko.badgeservice.model.Badge;
import com.zeljko.badgeservice.model.UserBadges;
import com.zeljko.badgeservice.repository.BadgeRepository;
import com.zeljko.badgeservice.repository.UserBadgesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final UserBadgesRepository userBadgesRepository;
    private final BadgeNotificationService badgeNotificationService;


    @KafkaListener(topics = "video-progress")
    public void processVideoProgress(VideoProgressMessage progressMessage) {
        log.info("Received Notification for Video - user id: {}, video id: {}, progress: {}, isMovieWatched: {}, genre: {}",
                progressMessage.getUserId()
                ,progressMessage.getVideoId()
                ,progressMessage.getProgress()
                ,progressMessage.getIsMovieWatched()
                ,progressMessage.getGenre()
        );

        if ("Marvel".equals(progressMessage.getGenre()) && progressMessage.getIsMovieWatched()) {
            activateBadgeForUser(String.valueOf(progressMessage.getUserId()), "Marvel Badge");
        }
    }

    public void activateBadgeForUser(String userId, String badgeName){
        Badge badge = badgeRepository.findByName(badgeName)
                .orElseThrow(() -> new BadgeNotFoundException("Badge with name " + badgeName + " not found"));


        UserBadges badges = userBadgesRepository.findById(userId)
                .orElseGet(() -> UserBadges.builder().userId(userId).badges(new ArrayList<>()).build());

        if(!badges.getBadges().contains(badge)){
            badges.getBadges().add(badge);
            userBadgesRepository.save(badges);
            badgeNotificationService.sendBadgeNotification(userId, "Uspesno ste dobili " + badge.getName());
        }
    }


    public Badge createBadge(MultipartFile file, String name, String description, boolean earned) throws IOException {
        Badge badge = new Badge();
        badge.setName(name);
        badge.setDescription(description);
        badge.setEarned(earned);
        badge.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

       return badgeRepository.insert(badge);
    }
}
