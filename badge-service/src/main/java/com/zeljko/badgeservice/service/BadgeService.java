package com.zeljko.badgeservice.service;

import com.zeljko.badgeservice.dto.VideoProgressMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BadgeService {


    @KafkaListener(topics = "video-progress", groupId = "badge-service-group")
    public void processVideoProgress(VideoProgressMessage progressMessage) {
        if (progressMessage.getIsMovieWatched()) {
            log.info("Super super");
        }
    }
}
