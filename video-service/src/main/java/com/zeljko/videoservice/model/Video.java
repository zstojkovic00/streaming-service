package com.zeljko.videoservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.InputStream;
import java.util.Map;

@Document(value = "video")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Video {

    @Id
    private String id;
    private String fileName;
    private String genre;
    private String contentType;
    private String description;
    private Long fileSize;
    private Long videoLength;

}
