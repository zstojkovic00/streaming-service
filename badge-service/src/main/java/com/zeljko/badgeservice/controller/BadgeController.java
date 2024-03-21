package com.zeljko.badgeservice.controller;

import com.zeljko.badgeservice.dto.UserDTO;
import com.zeljko.badgeservice.model.Badge;
import com.zeljko.badgeservice.model.UserBadges;
import com.zeljko.badgeservice.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/badge")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @PostMapping("/upload")
    public ResponseEntity<Void> createBadge(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description) {
        try {
            badgeService.createBadge(file, name, description);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/current-user")
    public ResponseEntity<Optional<UserBadges>> getBadgesForCurrentUser(@RequestHeader("Authorization") String bearerToken) {
        Optional<UserBadges> userBadges = badgeService.getBadgesForCurrentUser(bearerToken).getBody();

        return ResponseEntity.ok().body(userBadges);

    }
}
