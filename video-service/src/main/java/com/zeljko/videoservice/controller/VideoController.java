package com.zeljko.videoservice.controller;


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
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createVideo(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("photoUrl") String photoUrl,
                              @RequestParam("duration") String duration,
                              @RequestParam("ageRestriction") Integer ageRestriction,
                              @RequestParam("genre") String genre,
                              @RequestParam("file") MultipartFile videoFile) throws IOException {
        videoService.addVideo(title, description, photoUrl, duration, ageRestriction, genre, videoFile);
        String id = videoService.addVideo(title, description, photoUrl, duration, ageRestriction, genre, videoFile);
        return "redirect:/videos/" + id;
    }

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideo() throws Exception {
        List<Video> videos = videoService.getAllVideo();
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{id}")
    public Video getVideo(@PathVariable String id, Model model) throws Exception {
        Video video = videoService.getVideo(id);
        model.addAttribute("title", video.getTitle());
        model.addAttribute("url", "/videos/stream/" + id);
        return video;
    }

    @GetMapping("/stream/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
        Video video = videoService.getVideo(id);
        FileCopyUtils.copy(video.getStream(), response.getOutputStream());
    }

}
