package com.zeljko.videoservice.controller;


import com.zeljko.videoservice.dto.UpdateProgressVideo;
import com.zeljko.videoservice.dto.WatchedProgress;
import com.zeljko.videoservice.service.VideoProgressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/video-progress")
@RequiredArgsConstructor
@Slf4j
public class VideoProgressController {

    private final VideoProgressService videoProgressService;
    @PutMapping
    public ResponseEntity<String> updateVideoProgress(@RequestBody UpdateProgressVideo request) {

        videoProgressService.updateVideoProgress(request.getVideoId(), request.getUserId(), request.getProgress(),
                request.isWatched(), request.getGenre()
        );
        return ResponseEntity.ok("Video progress updated successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<WatchedProgress> getCurrentUserProgress(@RequestHeader("Authorization") String bearerToken,
                                                                  @PathVariable("id") String videoId) {

        WatchedProgress progress = videoProgressService.getCurrentUserProgress(bearerToken, videoId);
        return ResponseEntity.ok().body(progress);
    }

}
