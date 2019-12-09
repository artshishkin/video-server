package com.artarkatesoft.videomonitoring.videoserver.services;

import com.artarkatesoft.videomonitoring.videoserver.dao.VideoFileDAOwoSnapshotProjection;
import com.artarkatesoft.videomonitoring.videoserver.dto.VideoFileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface VideoFileService {
    List<VideoFileDTO> findAll();

    List<VideoFileDTO> findAllLimitedBy(Integer limit);

    List<VideoFileDAOwoSnapshotProjection> findAllByCameraNameLimitBy(String cameraName, Integer limit);
//    List<VideoFileDTO> findAllByCameraNameLimitBy(String cameraName, Integer limit);

    List<VideoFileDTO> findAllWithoutSnapshotLimitedBy(Integer limit);

    List<VideoFileDTO> findAllWithSnapshotLimitedBy(Integer limit);

    void save(VideoFileDTO file);

    void saveAll(Collection<VideoFileDTO> files);

    void fakeCreateSnapshots();

    byte[] findSnapshotByVideoFilePath(String videoFileName);

    void store(MultipartFile file, String fullFilePath);
}
