package com.zeljko.videoservice.controller;


import com.zeljko.videoservice.service.VideoService;
import com.zeljko.videoservice.service.impl.VideoServiceImpl;
import com.zeljko.videoservice.controller.dto.StreamBytesInfo;
import com.zeljko.videoservice.controller.dto.VideoMetadata;
import com.zeljko.videoservice.controller.dto.VideoMetadataRequest;
import jakarta.ws.rs.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("videos")
public class VideoController {

    private final VideoService videoService;
    private final static Logger log = LoggerFactory.getLogger(VideoController.class);

    public VideoController(VideoServiceImpl videoServiceImpl) {
        this.videoService = videoServiceImpl;
    }

    @GetMapping
    public List<VideoMetadata> findAll() {
        return videoService.findAllVideoMetadata();
    }

    @GetMapping("/{id}")
    public VideoMetadata findById(@PathVariable("id") String id) {
        return videoService.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @GetMapping(value = "/preview/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<StreamingResponseBody> getPreviewPicture(@PathVariable("id") String id) {
        InputStream inputStream = videoService.getVideoThumbnail(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(inputStream::transferTo);
    }


    @GetMapping("/stream/{id}")
    public ResponseEntity<StreamingResponseBody> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeHeader,
                                                             @PathVariable("id") String id) {

        List<HttpRange> httpRangeList = HttpRange.parseRanges(httpRangeHeader);
        StreamBytesInfo streamBytesInfo = videoService.getStreamBytes(id, !httpRangeList.isEmpty() ? httpRangeList.get(0) : null)
                .orElseThrow(NotFoundException::new);

        long byteLength = streamBytesInfo.rangeEnd() - streamBytesInfo.rangeStart() + 1;
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(!httpRangeList.isEmpty() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                .header("Content-Type", streamBytesInfo.contentType())
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", Long.toString(byteLength));

        if (!httpRangeList.isEmpty()) {
            builder.header("Content-Range",
                    "bytes " + streamBytesInfo.rangeStart() +
                            "-" + streamBytesInfo.rangeEnd() +
                            "/" + streamBytesInfo.fileSize());
        }

        return builder.body(streamBytesInfo.responseBody());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadVideo(VideoMetadataRequest videoMetadataRequest) {
        try {
            videoService.saveNewVideo(videoMetadataRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}