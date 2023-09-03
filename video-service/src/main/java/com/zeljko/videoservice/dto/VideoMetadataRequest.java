package com.zeljko.videoservice.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VideoMetadataRequest {

    private String description;
    private String genre;
    private MultipartFile file;
}
