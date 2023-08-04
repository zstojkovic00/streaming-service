package com.zeljko.videoservice.service;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.zeljko.videoservice.model.Video;
import com.zeljko.videoservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
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
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;


    public String addVideo(String title, String description, String photoUrl, String duration, Integer ageRestriction, String genre, MultipartFile videoFile) throws IOException {

        DBObject metaData = new BasicDBObject();
        ObjectId id = new ObjectId();

        metaData.put("type", "video");
        metaData.put("title", title);
        metaData.put("description", description);
        metaData.put("photoUrl", photoUrl);
        metaData.put("duration", duration);
        metaData.put("ageRestriction", ageRestriction);
        metaData.put("genre", genre);
        metaData.put("videoId", id.toString());

        gridFsTemplate.store(videoFile.getInputStream(), videoFile.getName(), videoFile.getContentType(), metaData);


        return id.toString();

    }

//        Video video = Video.builder()
//                .name(videoRequest.getName())
//                .description(videoRequest.getDescription())
//                .videoData(videoData)
//                .category(videoRequest.getCategory())
//                .duration(videoRequest.getDuration())
//                .genre(videoRequest.getGenre())
//                .ageRestriction(videoRequest.getAgeRestriction())
//                .releasedYear(String.valueOf(videoRequest.getReleasedYear()))
//                .creationDate(LocalDateTime.now())
//                .build();
//
//        videoRepository.save(video);
//        log.info("Video {} is saved", video.getId());

    public Video getVideo(String id) throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        Video video = new Video();
        video.setTitle(file.getMetadata().get("title").toString());
        video.setStream(gridFsOperations.getResource(file).getInputStream());
        return video;
    }


    public List<Video> getAllVideo() throws IOException {
        List<Video> videos = new ArrayList<>();

        GridFSFindIterable files = gridFsTemplate.find(new Query());
        files.forEach(file -> {
            Video video = new Video();
            video.setTitle(file.getMetadata().get("title").toString());
            try {
                video.setStream(gridFsTemplate.getResource(file).getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            videos.add(video);
        });

        return videos;
    }
}

//    public List<VideoResponse> getAllVideos() {
//       List<Video> videos = videoRepository.findAll();
//
//       return videos.stream().map(this::mapToVideoResponse).toList();
//    }
//
//    private VideoResponse mapToVideoResponse(Video video) {
//        return VideoResponse.builder()
//                .id(video.getId())
//                .name(video.getName())
//                .videoData(video.getVideoData())
//                .description(video.getDescription())
//                .category(video.getCategory())
//                .duration(video.getDuration())
//                .genre(video.getGenre())
//                .ageRestriction(video.getAgeRestriction())
//                .creationDate(video.getCreationDate())
//                .build();
//    }

