package com.zeljko.videoservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document("video_progress")
public class VideoProgress {

    @Id
    String id;
    String videoId;
    String userId;
    Double progress;
    boolean isMovieWatched;
    String genre;

    public VideoProgress(String videoId, String userId) {
    }
}
