package com.zeljko.videoservice.repository;

import com.zeljko.videoservice.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
