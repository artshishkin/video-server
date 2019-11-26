package com.artarkatesoft.videomonitoring.videoserver.services;

import com.artarkatesoft.videomonitoring.videoserver.model.VideoFile;

import java.util.Collection;
import java.util.List;

public interface VideoFileService {
    List<VideoFile> findAll();

    void save(VideoFile file);

    void saveAll(Collection<VideoFile> files);
}
