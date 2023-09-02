package com.zeljko.videoservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoMetadata {

    private String id;
    private String description;
    private String contentType;
    private String previewUrl;
    private String streamUrl;
}
