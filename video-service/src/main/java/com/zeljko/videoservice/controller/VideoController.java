package com.zeljko.videoservice.controller;


import com.zeljko.videoservice.dto.UpdateProgressRequest;
import com.zeljko.videoservice.model.Video;
import com.zeljko.videoservice.service.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createVideo(@RequestParam("title") String title,
                                              @RequestParam("description") String description,
                                              @RequestParam("photoUrl") String photoUrl,
                                              @RequestParam("duration") String duration,
                                              @RequestParam("ageRestriction") Integer ageRestriction,
                                              @RequestParam("genre") String genre,
                                              @RequestParam("file") MultipartFile videoFile) throws IOException {
        String response = videoService.addVideo(title, description, photoUrl, duration, ageRestriction, genre, videoFile);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideo() {
        List<Video> videos = videoService.getAllVideo();
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideo(@PathVariable String id, Model model) {
        Video video = videoService.getVideo(id);
        model.addAttribute("title", video.getTitle());
        model.addAttribute("url", "/videos/stream/" + id);
        return ResponseEntity.ok(video);
    }

    @GetMapping("/stream/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
        Video video = videoService.getVideo(id);
        // copies the content of the video's input stream to the output stream of the http response
        FileCopyUtils.copy(video.getStream(), response.getOutputStream());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable String id) {
        videoService.deleteVideo(id);
        return ResponseEntity.ok("Video deleted successfully");

    }

    @PutMapping("/progress")
    public ResponseEntity<String> updateVideoProgress(@RequestBody UpdateProgressRequest request) {
        videoService.updateVideoProgress(request.getUserId(), request.getVideoId(), request.getProgress());
        return ResponseEntity.ok("Video progress updated successfully");
    }

}
