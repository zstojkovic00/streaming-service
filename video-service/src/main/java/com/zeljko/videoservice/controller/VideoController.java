package com.zeljko.videoservice.controller;

import com.zeljko.videoservice.dto.VideoRequest;
import com.zeljko.videoservice.dto.VideoResponse;
import com.zeljko.videoservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVideo(@RequestBody VideoRequest videoRequest){
        videoService.createVideo(videoRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VideoResponse> getAllVideos(){
        return  videoService.getAllVideos();
    }

}
