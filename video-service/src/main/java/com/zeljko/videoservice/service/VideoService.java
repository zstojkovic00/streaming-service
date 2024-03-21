package com.zeljko.videoservice.service;

import com.zeljko.videoservice.dto.VideoMetadata;
import com.zeljko.videoservice.dto.VideoMetadataRequest;
import com.zeljko.videoservice.model.StreamBytesInfo;
import com.zeljko.videoservice.model.Video;
import com.zeljko.videoservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.zeljko.videoservice.utils.Utils.removeFileExt;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    @Value("${data.folder}")
    private String dataFolder;

    private final VideoRepository videoRepository;
    private final FrameGrabberService frameGrabberService;


    public List<VideoMetadata> findAllVideoMetadata() {
        return videoRepository.findAll().stream()
                .map(VideoService::convert)
                .collect(Collectors.toList());
    }


    public Optional<VideoMetadata> findById(String id) {
        return videoRepository.findById(id).map(VideoService::convert);
    }

    @Transactional
    public void saveNewVideo(VideoMetadataRequest videoMetadataRequest) {
        Video v = new Video();
        v.setFileName(videoMetadataRequest.getFile().getOriginalFilename());
        v.setContentType(videoMetadataRequest.getFile().getContentType());
        v.setFileSize(videoMetadataRequest.getFile().getSize());
        v.setDescription(videoMetadataRequest.getDescription());
        v.setGenre(videoMetadataRequest.getGenre());

        videoRepository.save(v);

        Path directory = Path.of(dataFolder, v.getId());

        try {
            Files.createDirectory(directory);
            Path file = Path.of(directory.toString(), videoMetadataRequest.getFile().getOriginalFilename());
            try (OutputStream outputStream = Files.newOutputStream(file, CREATE, WRITE)) {
                videoMetadataRequest.getFile().getInputStream().transferTo(outputStream);
            }
            long videoLength = frameGrabberService.generatePreviewPictures(file);
            v.setVideoLength(videoLength);
            videoRepository.save(v);

        } catch (IOException e) {
            log.error("", e);
            throw new IllegalStateException();
        }
    }


    public Optional<InputStream> getVideoThumbnail(String id) {
        return videoRepository.findById(id)
                .flatMap(vmd -> {
                    Path previewPicturePath = Path.of(dataFolder,
                            vmd.getId().toString(),
                            removeFileExt(vmd.getFileName()) + ".jpeg");
                    if (!Files.exists(previewPicturePath)) {
                        return Optional.empty();
                    }
                    try {
                        return Optional.of(Files.newInputStream(previewPicturePath));
                    } catch (IOException ex) {
                        log.error("", ex);
                        return Optional.empty();
                    }
                });
    }


    public static VideoMetadata convert(Video v) {
        VideoMetadata videoMetadata = new VideoMetadata();
        videoMetadata.setId(v.getId());
        videoMetadata.setDescription(v.getDescription());
        videoMetadata.setContentType(v.getContentType());
        videoMetadata.setPreviewUrl("/api/v1/video/preview/" + v.getId());
        videoMetadata.setStreamUrl("/api/v1/video/stream/" + v.getId());
        videoMetadata.setGenre(v.getGenre());

        return videoMetadata;
    }


    public Optional<StreamBytesInfo> getStreamBytes(String id, HttpRange range) {
        Optional<Video> byId = videoRepository.findById(id);
        if (byId.isEmpty()) {
            return Optional.empty();
        }
        Path filePath = Path.of(dataFolder, id, byId.get().getFileName());
        if (!Files.exists(filePath)) {
            log.error("File {} not found", filePath);
            return Optional.empty();
        }
        try {
            long fileSize = Files.size(filePath);
            long chunkSize = fileSize / 100;
            if (range == null) {
                return Optional.of(new StreamBytesInfo(
                        out -> Files.newInputStream(filePath).transferTo(out),
                        fileSize, 0, fileSize, byId.get().getContentType()));
            }

            long rangeStart = range.getRangeStart(0);
            long rangeEnd = rangeStart + chunkSize; // range.getRangeEnd(fileSize);
            if (rangeEnd >= fileSize) {
                rangeEnd = fileSize - 1;
            }
            long finalRangeEnd = rangeEnd;
            return Optional.of(new StreamBytesInfo(
                    out -> {
                        try (InputStream inputStream = Files.newInputStream(filePath)) {
                            inputStream.skip(rangeStart);
                            byte[] bytes = inputStream.readNBytes((int) ((finalRangeEnd - rangeStart) + 1));
                            out.write(bytes);
                        }
                    },
                    fileSize, rangeStart, rangeEnd, byId.get().getContentType()));
        } catch (IOException ex) {
            log.error("", ex);
            return Optional.empty();
        }
    }
}