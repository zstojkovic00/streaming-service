package com.zeljko.badgeservice.service;

import com.zeljko.badgeservice.dto.UserDTO;
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
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final UserBadgesRepository userBadgesRepository;
    private final RestTemplate restTemplate;



    @KafkaListener(topics = "video-progress")
    public void processVideoProgress(VideoProgressMessage progressMessage) {
        log.info("user id: {}, video id: {}, progress: {}, isMovieWatched: {}, genre: {}",
                progressMessage.getVideoId()
                ,progressMessage.getUserId()
                ,progressMessage.getProgress()
                ,progressMessage.isWatched()
                ,progressMessage.getGenre()
        );

        if ("Marvel".equals(progressMessage.getGenre()) && progressMessage.isWatched()) {
                activateBadgeForUser(progressMessage.getVideoId(), "Marvel Badge");
        }
    }

    public void activateBadgeForUser(String userId, String badgeName) {
        Badge badge = badgeRepository.findByName(badgeName)
                .orElseThrow(() -> new BadgeNotFoundException("Badge with name " + badgeName + " not found"));

        try {
            UserBadges badges = userBadgesRepository.findByUserId(userId)
                    .orElseGet(() -> UserBadges.builder().userId(userId).badges(new ArrayList<>()).build());


            if (!badges.getBadges().contains(badge)) {
                badges.getBadges().add(badge);
                userBadgesRepository.save(badges);
                log.info("Bedz aktiviran");
            } else {
                log.info("Korisnik već ima ovaj bedž");
            }
        } catch (Exception e) {
            log.error("Error activating badge for user: " + e.getMessage(), e);
        }
    }

    public Badge createBadge(MultipartFile file, String name, String description) throws IOException {
        Badge badge = new Badge();
        badge.setName(name);
        badge.setDescription(description);
        badge.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
       return badgeRepository.insert(badge);
    }


    public ResponseEntity<Optional<UserBadges>> getBadgesForCurrentUser() {
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "http://localhost:8080/user/current",
                HttpMethod.GET,
                null,
                UserDTO.class
        );
        if(response.getStatusCode() == HttpStatus.OK){
            UserDTO userDTO = response.getBody();

            int userId = userDTO.getId();

            Optional<UserBadges> badges = userBadgesRepository.findById(String.valueOf(userId));

            return ResponseEntity.ok(badges);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
