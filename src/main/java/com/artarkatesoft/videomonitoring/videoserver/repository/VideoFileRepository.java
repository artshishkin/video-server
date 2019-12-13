package com.artarkatesoft.videomonitoring.videoserver.repository;

import com.artarkatesoft.videomonitoring.videoserver.dao.VideoFileDAO;
import com.artarkatesoft.videomonitoring.videoserver.dao.VideoFileDAOwoSnapshotProjection;
import com.artarkatesoft.videomonitoring.videoserver.dto.VideoFileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public interface VideoFileRepository extends JpaRepository<VideoFileDAO, Long> {

    @Modifying
    @Query(value = "insert into video_file (camera_name, date, file_path, file_name, size, video_type) values (:camera_name, :date, :file_path, :file_name, :size, :video_type)  on conflict do nothing", nativeQuery = true)
    @Transactional
    void saveIgnore(
            @Param("file_name") String fileName,
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



    List<VideoFileDAO> findTop10ByOrderByDateDesc();

    Page<VideoFileDAO> findAllBySnapshotIsNull(Pageable pageable);

    List<VideoFileDAO> findAllBySnapshotIsNull();
    Page<VideoFileDAO> findAllBySnapshotIsNotNull(Pageable pageable);

    List<VideoFileDAO> findAllBySnapshotIsNotNull();

    //    VideoFileDAO findByFilePath(String filePath);
    VideoFileDAO findOneByFilePath(String filePath);

    VideoFileDAO findOneByFilePathContains(String filePath);

//    List<VideoFileDTO> findAllBy(Sort sort);

//    Page<VideoFileDTO> findAllBy(Pageable pageable);

    //    Page<VideoFileDTO> findAllByCameraName(String cameraName, Pageable pageable);
    Page<VideoFileDAOwoSnapshotProjection> findAllByCameraName(String cameraName, Pageable pageable);

    Page<VideoFileDAOwoSnapshotProjection> findAllByCameraNameAndVideoType(String cameraName, String videoType, Pageable pageable);


    List<VideoFileDAOwoSnapshotProjection> findAllByOrderByDateDesc();
    Page<VideoFileDAOwoSnapshotProjection> findAllByOrderByDateDesc(Pageable pageable);



    @Transactional
    void deleteByFilePath(String filePath);

//    @Transactional
//    Stream<VideoFileDAOwoSnapshotProjection> findAllByOrderByDateDesc();
// TODO: 11.12.2019 Mistake must be fixed
//    org.springframework.dao.InvalidDataAccessApiUsageException: You're trying to execute a streaming query method without a surrounding transaction that keeps the connection open so that the Stream can actually be consumed. Make sure the code consuming the stream uses @Transactional or any other way of declaring a (read-only) transaction.

// TODO: 12.12.2019  
//    There was an unexpected error (type=Internal Server Error, status=500).
//    could not extract ResultSet; nested exception is org.hibernate.exception.GenericJDBCException: could not extract ResultSet


    @Query(value = "select v.filePath from VideoFileDAO v")
    List<String> findAllPaths();

    @Query(value = "select v.filePath from VideoFileDAO v order by v.date desc")
    List<String> findAllPathsOrderByDateDesc();

    @Query(value = "select v.filePath from VideoFileDAO v")
    List<String> findAllPaths(Pageable pageable);

}
