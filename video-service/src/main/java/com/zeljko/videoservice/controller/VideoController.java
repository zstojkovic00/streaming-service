package com.zeljko.videoservice.controller;


import com.zeljko.videoservice.dto.VideoMetadata;
import com.zeljko.videoservice.dto.VideoMetadataRequest;
import com.zeljko.videoservice.service.VideoService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/all")
    public List<VideoMetadata> findAll() {

        return videoService.findAllVideoMetadata();
    }

    @GetMapping("/{id}")
    public VideoMetadata findById(@PathVariable("id") String id) {

        return videoService.findById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = "/preview/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<StreamingResponseBody> getPreviewPicture(@PathVariable("id") String id) {
        // StreamingResponseBody
        InputStream inputStream = videoService.getPreviewInputStream(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(inputStream::transferTo);
    }


    @GetMapping("/stream/{id}")
    public ResponseEntity<StreamingResponseBody> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeHeader,
                                                             @PathVariable("id") String id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadVideo(VideoMetadataRequest videoMetadataRequest) {
        try {
          videoService.saveNewVideo(videoMetadataRequest);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
