package com.zeljko.videoservice.repository.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(value = "video")
public class Video {

    @Id
    private String id;
    private String fileName;
    private List<String> genre;
    private String contentType;
    private String description;
    private Long fileSize;
    private Long videoLength;

    public Video(String id, String fileName, List<String> genre, String contentType, String description, Long fileSize, Long videoLength) {
        this.id = id;
        this.fileName = fileName;
        this.genre = genre;
        this.contentType = contentType;
        this.description = description;
        this.fileSize = fileSize;
        this.videoLength = videoLength;
    }

    public Video() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(Long videoLength) {
        this.videoLength = videoLength;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", genre='" + genre + '\'' +
                ", contentType='" + contentType + '\'' +
                ", description='" + description + '\'' +
                ", fileSize=" + fileSize +
                ", videoLength=" + videoLength +
                '}';
    }
}
