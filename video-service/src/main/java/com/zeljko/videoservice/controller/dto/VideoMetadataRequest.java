package com.zeljko.videoservice.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record VideoMetadataRequest (String description,
                                    List<String> genre,
                                    MultipartFile file) {}
