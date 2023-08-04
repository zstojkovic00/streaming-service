package com.zeljko.videoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {

    private String id;
    private String name;
    private String description;
    private String category;
    private String duration;
    private String genre;
    private String ageRestriction;
    private Integer releasedYear;
    private LocalDateTime creationDate;

}
