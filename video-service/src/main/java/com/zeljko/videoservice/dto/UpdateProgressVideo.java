package com.zeljko.videoservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProgressVideo {

    String videoId;
    String userId;
    Double progress;
    boolean isMovieWatched;
    String genre;
}
