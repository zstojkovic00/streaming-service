package com.zeljko.videoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoRequest {

    private String name;
    private String description;
    private String url;
    private String category;
    private Date creationDate;
}
