package com.zeljko.videoservice.service;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zeljko.videoservice.event.VideoProgressMessage;
import com.zeljko.videoservice.model.Video;
import com.zeljko.videoservice.model.VideoProgress;
import com.zeljko.videoservice.repository.VideoProgressRepository;
import com.zeljko.videoservice.repository.VideoRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoProgressRepository videoProgressRepository;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;
    private final KafkaTemplate<String, VideoProgressMessage> kafkaTemplate;


    public String addVideo(String title, String description, String photoUrl, String duration, Integer ageRestriction, String genre, MultipartFile videoFile) throws IOException {

        DBObject metaData = new BasicDBObject();
        metaData.put("type", "video");
        metaData.put("title", title);
        metaData.put("description", description);
        metaData.put("photoUrl", photoUrl);
        metaData.put("duration", duration);
        metaData.put("ageRestriction", ageRestriction);
        metaData.put("genre", genre);

        Object id = gridFsTemplate.store(
                videoFile.getInputStream(), videoFile.getName(), videoFile.getContentType(), metaData);

        return id.toString();
    }

    public Video getVideo(String id) {
        try {
            GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

            if (file == null) {
                log.info("Video not found with id {}" ,id);
                throw new NotFoundException("Video not found with id: " + id);
            }

            Video video = new Video();
            video.setId(file.getObjectId().toString());
            video.setTitle(file.getMetadata().get("title").toString());
            video.setStream(gridFsOperations.getResource(file).getInputStream());
            return video;
        } catch (Exception e) {
            log.error("An error occurred while retrieving the video with id {}", id, e);
            throw new RuntimeException("Failed to retrieve the video");
        }
    }


    public List<Video> getAllVideo() {
        List<Video> videos = new ArrayList<>();

        try {
            GridFSFindIterable files = gridFsTemplate.find(new Query());
            files.forEach(file -> {
                Video video = new Video();
                video.setId(file.getObjectId().toString());
                video.setTitle(file.getMetadata().get("title").toString());
                try {
                    video.setStream(gridFsTemplate.getResource(file).getInputStream());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                videos.add(video);
            });
        } catch (RuntimeException e) {
            log.error("Error retrieving videos: {}", e.getMessage());
            throw new RuntimeException("An error occurred while retrieving videos");
        }
        return videos;
    }


    public void deleteVideo(String id) {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        if (file != null) {
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
            videoRepository.deleteById(id);
            log.info("Deleted video with id: {}", id);
        } else {
            log.warn("Video with id {} not found.", id);
            throw new NotFoundException("Video not found with id: " + id);
        }
    }

    public void updateVideoProgress(Integer userId, String videoId, double progress, boolean isMovieWatched) {
        log.debug("Updating video progress for userId: {}, videoId: {}, progress: {}, is movie watched: {}", userId, videoId, progress, isMovieWatched);

        VideoProgress videoProgress = videoProgressRepository.findByUserIdAndVideoId(userId, videoId)
                .orElseGet(() -> new VideoProgress(userId, videoId));

        videoProgress.setProgress(progress);
        videoProgress.setMovieWatched(isMovieWatched);

        videoProgressRepository.save(videoProgress);

        kafkaTemplate.send("video-progress",new VideoProgressMessage
                (videoProgress.getUserId(),
                        videoProgress.getVideoId(),
                        videoProgress.getProgress(),
                        videoProgress.isMovieWatched()));
    }

}


