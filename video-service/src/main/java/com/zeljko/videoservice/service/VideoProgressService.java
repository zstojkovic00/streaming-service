package com.zeljko.videoservice.service;

import com.zeljko.videoservice.dto.UserDTO;
import com.zeljko.videoservice.dto.WatchedProgress;
import com.zeljko.videoservice.dto.VideoProgressMessage;
import com.zeljko.videoservice.model.VideoProgress;
import com.zeljko.videoservice.repository.VideoProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${api.base}")
    private String apiBase;

    public void updateVideoProgress(String videoId, String userId, Double progress, boolean watched, String genre) {
        log.info("Updating video progress for videoId: {}, userId: {}, progress: {}, is movie watched: {}, genre: {}", videoId, userId, progress, watched, genre);

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

        kafkaTemplate.send("video-progress", new VideoProgressMessage(videoProgress.getVideoId(), videoProgress.getUserId(),
                videoProgress.getProgress(), videoProgress.isWatched(), videoProgress.getGenre()));
    }

    public Optional<WatchedProgress> findCurrentUserProgress(String bearerToken, String videoId) {

        String userId = findCurrentUserId(bearerToken);

        if (userId.isEmpty()) {
            return Optional.empty();
        }

        return videoProgressRepository.findProgressByUserIdAndVideoId(userId, videoId);
    }

    private String findCurrentUserId(String bearerToken) {
        var headers = new HttpHeaders();
        String token = bearerToken.substring(7);
        headers.setBearerAuth(token);

        var entity = new HttpEntity<>(headers);
        var response = restTemplate.exchange(apiBase, HttpMethod.GET, entity, UserDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            var userDTO = response.getBody();

            String userId = String.valueOf(userDTO.getId());
            return userId;
        }
        return null;
    }
}