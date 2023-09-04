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
    private String id;
    private String videoId;
    private String userId;
    private Double progress;
    private boolean watched;
    private String genre;

    public VideoProgress(String videoId, String userId) {
    }
}
