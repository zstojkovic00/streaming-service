package com.zeljko.videoservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "video_progress")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VideoProgress {

    @Id
    private String id;
    private Integer userId;
    private String videoId;
    private double progress;

    public VideoProgress(Integer userId, String videoId) {
        this.userId = userId;
        this.videoId = videoId;
    }
}
