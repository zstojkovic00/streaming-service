package com.zeljko.videoservice.repository;

import com.zeljko.videoservice.dto.WatchedProgress;
import com.zeljko.videoservice.model.VideoProgress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface VideoProgressRepository extends MongoRepository<VideoProgress, String> {


    Optional<VideoProgress> findByVideoIdAndUserId(String videoId, String userId);

    @Query(value = "{'userId' : ?0, 'videoId' : ?1}", fields = "{'progress' : 1}")
    WatchedProgress findProgressByUserIdAndVideoId(String userId, String videoId);


}
