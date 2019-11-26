package com.artarkatesoft.videomonitoring.videoserver.repository;

import com.artarkatesoft.videomonitoring.videoserver.model.VideoFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface VideoFileRepository extends JpaRepository<VideoFile, Long> {

    @Modifying
    @Query(value = "insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (:camera_name, :date, :file_path, :full_name, :size, :video_type)  on conflict do nothing", nativeQuery = true)
    @Transactional
    void saveIgnore(
            @Param("full_name") String fullName,
            @Param("file_path") String filePath,
            @Param("size") Long size,
            @Param("date") Date date,
            @Param("camera_name") String cameraName,
            @Param("video_type") String videoType
    );

//    @Modifying
//    @Query(value="insert into public.city_info(city, country) values(:city,:country) on conflict do nothing returning city_id",nativeQuery = true)
//    @Transactional
//    Integer save(@Param("city")String city,@Param("country") String country);
}
