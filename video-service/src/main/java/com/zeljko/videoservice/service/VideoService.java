package com.zeljko.videoservice.service;

import com.zeljko.videoservice.model.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    String addVideo(String title,
                    String description,
                    String photoUrl,
                    String duration,
                    Integer ageRestriction,
                    String genre,
                    MultipartFile videoFile) throws IOException;

    List<Video> getAllVideo();

    Video getVideo(String id);

    void deleteVideo(String id);

    void updateVideoProgress(Integer userId, String videoId, Double progress, Boolean isMovieWatched);
}
