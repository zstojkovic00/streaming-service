package com.zeljko.videoservice.controller.dto;

import java.util.List;

public record VideoMetadata (String id,
                             String description,
                             String contentType,
                             String previewUrl,
                             String streamUrl,
                             List<String> genre) {}
