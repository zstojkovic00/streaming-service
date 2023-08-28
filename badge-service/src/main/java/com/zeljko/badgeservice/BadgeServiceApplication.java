package com.zeljko.badgeservice;

import com.zeljko.badgeservice.dto.VideoProgressMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class BadgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BadgeServiceApplication.class, args);
	}

	@KafkaListener(topics = "video-progress")
	public void processVideoProgress(VideoProgressMessage progressMessage) {
		log.info("Received Notification for Video - user id: {}, video id: {}, progress: {}, isMovieWatched: {}", progressMessage.getUserId(), progressMessage.getVideoId(), progressMessage.getProgress(), progressMessage.getIsMovieWatched());
	}

}
