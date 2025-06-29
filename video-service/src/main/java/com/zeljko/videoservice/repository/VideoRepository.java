package com.zeljko.videoservice.repository;

import com.zeljko.videoservice.repository.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
