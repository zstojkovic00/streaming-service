package com.zeljko.videoservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoProgressMessage {

    private Integer userId;
    private String videoId;
    private Double progress;
    private Boolean isMovieWatched;
}


