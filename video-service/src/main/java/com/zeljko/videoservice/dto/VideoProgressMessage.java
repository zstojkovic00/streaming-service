package com.zeljko.videoservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VideoProgressMessage {

    private String userId;
    private String videoId;
    private Double progress;
    private Boolean isMovieWatched;
    private String genre;
}
