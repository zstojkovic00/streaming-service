package com.zeljko.videoservice.service;

import com.zeljko.videoservice.controller.dto.StreamBytesInfo;
import com.zeljko.videoservice.controller.dto.VideoMetadata;
import com.zeljko.videoservice.controller.dto.VideoMetadataRequest;
import org.springframework.http.HttpRange;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface VideoService {
    List<VideoMetadata> findAllVideoMetadata();

    Optional<VideoMetadata> findById(String id);

    void saveNewVideo(VideoMetadataRequest request) throws IOException;

    Optional<InputStream> getVideoThumbnail(String id);

    Optional<StreamBytesInfo> getStreamBytes(String id, HttpRange range);
}
