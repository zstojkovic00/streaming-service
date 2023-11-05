package com.zeljko.videoservice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProgressVideo {

    private String videoId;
    private String userId;
    private Double progress;
    private boolean watched;
    private String genre;
}
