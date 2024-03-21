package com.zeljko.badgeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoProgressMessage {

    private String videoId;
    private String userId;
    private Double progress;
    private boolean watched;
    private String genre;
}
