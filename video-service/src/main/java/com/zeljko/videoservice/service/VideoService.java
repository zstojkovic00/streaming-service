package com.zeljko.videoservice.service;


import com.zeljko.videoservice.dto.VideoRequest;
import com.zeljko.videoservice.dto.VideoResponse;
import com.zeljko.videoservice.model.Video;
import com.zeljko.videoservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;

    public void createVideo(VideoRequest videoRequest){

        Video video = Video.builder()
                .name(videoRequest.getName())
                .description(videoRequest.getDescription())
                .url(videoRequest.getUrl())
                .category(videoRequest.getCategory())
                .creationDate(LocalDateTime.now())
                .build();

        videoRepository.save(video);
        log.info("Video {} is saved", video.getId());

    }

    public List<VideoResponse> getAllVideos() {
       List<Video> videos = videoRepository.findAll();

       return videos.stream().map(this::mapToVideoResponse).toList();
    }

    private VideoResponse mapToVideoResponse(Video video) {
        return VideoResponse.builder()
                .id(video.getId())
                .name(video.getName())
                .description(video.getDescription())
                .url(video.getUrl())
                .category(video.getCategory())
                .creationDate(video.getCreationDate())
                .build();
    }
}
