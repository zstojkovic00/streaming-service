package com.zeljko.videoservice.service;

import com.zeljko.videoservice.dto.VideoMetadata;
import com.zeljko.videoservice.dto.VideoMetadataRequest;
import com.zeljko.videoservice.model.Video;
import com.zeljko.videoservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import static com.zeljko.videoservice.Utils.Utils.removeFileExt;
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


    public Optional<InputStream> getPreviewInputStream(String id) {
        return videoRepository.findById(id)
                .flatMap(
                        vm -> {
                            Path previewPicturePath = Path.of(dataFolder, vm.getId(), removeFileExt(vm.getFileName() + ".jpeg"));
                            if (!Files.exists(previewPicturePath)) {
                                return Optional.empty();
                            }
                            try {

                                return Optional.of(Files.newInputStream(previewPicturePath));

                            } catch (IOException e) {
                                log.error("", e);
                                throw new IllegalStateException();
                            }
                        }
                );
    }


    public static VideoMetadata convert(Video v) {
        VideoMetadata videoMetadata = new VideoMetadata();
        videoMetadata.setId(v.getId());
        videoMetadata.setDescription(v.getDescription());
        videoMetadata.setContentType(v.getContentType());
        videoMetadata.setPreviewUrl("/api/v1/video/preview/" + v.getId());
        videoMetadata.setStreamUrl("/api/v1/video/stream/" + v.getId());

        return videoMetadata;
    }


}