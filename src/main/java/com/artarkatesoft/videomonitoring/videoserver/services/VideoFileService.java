package com.artarkatesoft.videomonitoring.videoserver.services;

import com.artarkatesoft.videomonitoring.videoserver.model.VideoFile;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface VideoFileService {
    List<VideoFile> findAll();

    List<VideoFile> findAllLimitedBy(int limit);

    void save(VideoFile file);

    void saveAll(Collection<VideoFile> files);
}
