package com.zeljko.videoservice.controller;


import com.zeljko.videoservice.dto.UpdateProgressVideo;
import com.zeljko.videoservice.dto.WatchedProgress;
import com.zeljko.videoservice.service.VideoProgressService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/video/progress")
@RequiredArgsConstructor
@Slf4j
public class VideoProgressController {

    private final VideoProgressService videoProgressService;

    @PutMapping
    public ResponseEntity<?> updateVideoProgress(@RequestBody UpdateProgressVideo request) {
        try {
            videoProgressService.updateVideoProgress(request.getVideoId(), request.getUserId(), request.getProgress(),
                    request.isWatched(), request.getGenre());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body("Video progress updated successfully");
    }

    @GetMapping("/{id}")
    public WatchedProgress findCurrentUserProgress(@RequestHeader("Authorization") String bearerToken,
                                                   @PathVariable("id") String videoId) {

        return videoProgressService.findCurrentUserProgress(bearerToken, videoId).orElseThrow(NotFoundException::new);
    }

}
