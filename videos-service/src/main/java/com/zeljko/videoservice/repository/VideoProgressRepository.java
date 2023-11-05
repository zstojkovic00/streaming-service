package com.zeljko.videoservice.repository;

import com.zeljko.videoservice.model.VideoProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VideoProgressRepository extends MongoRepository<VideoProgress, String> {
    Optional<VideoProgress> findByVideoIdAndUserId(String videoId, String userId);
}
