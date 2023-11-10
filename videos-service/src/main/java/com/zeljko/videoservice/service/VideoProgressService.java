package com.zeljko.videoservice.service;

import com.zeljko.videoservice.dto.UserDTO;
import com.zeljko.videoservice.dto.WatchedProgress;
import com.zeljko.videoservice.dto.VideoProgressMessage;
import com.zeljko.videoservice.model.VideoProgress;
import com.zeljko.videoservice.repository.VideoProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoProgressService {

    private final KafkaTemplate<String, VideoProgressMessage> kafkaTemplate;
    private final VideoProgressRepository videoProgressRepository;
    private final RestTemplate restTemplate;


    public void updateVideoProgress(String videoId, String userId, Double progress, boolean watched, String genre) {
        log.debug("Updating video progress for videoId: {}, userId: {}, progress: {}, is movie watched: {}, genre: {}", videoId, userId, progress, watched, genre);


        if (userId == null || userId.isEmpty()) {
            log.info("userId is null or empty, exiting updateVideoProgress");
            return;
        }

        Optional<VideoProgress> existingProgress = videoProgressRepository.findByVideoIdAndUserId(videoId, userId);
        VideoProgress videoProgress;

        if (existingProgress.isPresent()) {
            videoProgress = existingProgress.get();
        } else {
            videoProgress = new VideoProgress();
            videoProgress.setVideoId(videoId);
            videoProgress.setUserId(userId);
        }

        videoProgress.setProgress(progress);
        videoProgress.setWatched(watched);
        videoProgress.setGenre(genre);

        videoProgressRepository.save(videoProgress);

        kafkaTemplate.send("video-progress", new VideoProgressMessage(
                videoProgress.getVideoId(),
                videoProgress.getUserId(),
                videoProgress.getProgress(),
                videoProgress.isWatched(),
                videoProgress.getGenre()
        ));
    }

    public WatchedProgress getCurrentUserProgress(String bearerToken, String videoId) {

        HttpHeaders httpHeaders = new HttpHeaders();
        bearerToken = bearerToken.substring(7);
        httpHeaders.setBearerAuth(bearerToken);

        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "http://localhost:8080/user/current",
                HttpMethod.GET,
                entity,
                UserDTO.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            UserDTO userDTO = response.getBody();

            String userId = String.valueOf(userDTO.getId());

            return videoProgressRepository.findProgressByUserIdAndVideoId(userId, videoId);
        } else {
            return null;
        }
    }
}