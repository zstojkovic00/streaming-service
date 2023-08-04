package com.zeljko.videoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoRequest {

    private String name;
    private String description;
    private byte[] videoData;
    private String category;
    private String duration;
    private String genre;
    private String ageRestriction;
    private Integer releasedYear;
    private LocalDateTime creationDate;
}
