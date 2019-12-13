package com.artarkatesoft.videomonitoring.videoserver.services;

import com.artarkatesoft.videomonitoring.videoserver.aop.TrackTime;
import com.artarkatesoft.videomonitoring.videoserver.dao.VideoFileDAO;
import com.artarkatesoft.videomonitoring.videoserver.dao.VideoFileDAOwoSnapshotProjection;
import com.artarkatesoft.videomonitoring.videoserver.dto.VideoFileDTO;
import com.artarkatesoft.videomonitoring.videoserver.repository.VideoFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseVideoFileServiceImpl implements VideoFileService {

    @Autowired
    private VideoFileRepository repository;

    @Value("${com.artarkatesoft.cam.video-server.video-file-service.files-limit:#{null}}")
    private Long filesCountLimit;

    @TrackTime
    @Override
    public List<VideoFileDTO> findAll() {
        return repository.findAllByOrderByDateDesc()
                .stream()
                .map(VideoFileDTO::new)
                .collect(Collectors.toList());
//        return repository.findAllBy(Sort.by(Sort.Direction.DESC, "date"));


//        return repository
//                .findAll(Sort.by(Sort.Direction.DESC, "date"))
//                .stream()
//                .map(VideoFileDTO::new)
//                .collect(Collectors.toList());
    }

    @Override
    public List<VideoFileDTO> findAllLimitedBy(Integer limit) {
//        Page<VideoFileDAO> page = repository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));
//        List<VideoFileDTO> videoFileDTOList = page
//                .get()
//                .map(VideoFileDTO::new)
//                .collect(Collectors.toList());


//        Page<VideoFileDTO> page = repository.findAllBy(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));
//        // TODO: 06.12.2019 Refactor that not to call findAll() here
//        List<VideoFileDTO> videoFileDTOList = page.toList();
//        return videoFileDTOList;


        return repository.findAllByOrderByDateDesc(PageRequest.of(0, limit))
                .stream()
                .map(VideoFileDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoFileDAOwoSnapshotProjection> findAllByCameraNameLimitBy(String cameraName, Integer limit) {
        if (limit == null) limit = 100;
        Page<VideoFileDAOwoSnapshotProjection> page = repository.findAllByCameraName(cameraName, PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));

        return page.toList();

    }

    @Override
    public List<VideoFileDAOwoSnapshotProjection> findAllByCameraNameAndVideoTypeLimitBy(String cameraName, String videoType, Integer limit) {
        if (limit == null) limit = 100;
        Page<VideoFileDAOwoSnapshotProjection> page = repository.findAllByCameraNameAndVideoType(cameraName,
                videoType, PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));

        return page.toList();
    }


//    @Override
//    public List<VideoFileDTO> findAllByCameraNameLimitBy(String cameraName, Integer limit) {
//        if (limit == null) limit = 100;
//        List<VideoFileDTO> videoFileDTOList = repository
//                .findAllByCameraName(cameraName, PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")))
//                .get()
//                .map(VideoFileDTO::new)
//                .collect(Collectors.toList());
//
//        return videoFileDTOList;
//
//    }
//


//    @Override
//    public List<VideoFileDTO> findAllByCameraNameLimitBy(String cameraName, Integer limit) {
//        if (limit == null) limit = 100;
//        Page<VideoFileDTO> page = repository.findAllByCameraName(cameraName, PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));
//
//        return page.toList();
//
//    }

    @Override
    public List<VideoFileDTO> findAllWithoutSnapshotLimitedBy(Integer limit) {
        if (limit == null) return repository
                .findAllBySnapshotIsNull()
                .stream()
                .map(VideoFileDTO::new)
                .collect(Collectors.toList());
        Page<VideoFileDAO> page = repository.findAllBySnapshotIsNull(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));
        List<VideoFileDTO> videoFileDTOList = page.get()
                .map(VideoFileDTO::new)
                .collect(Collectors.toList());
        return videoFileDTOList;

//        if (limit == null) return repository
//                .findAllBySnapshotIsNull()
//                .stream()
//                .map(VideoFileDTO::new)
//                .collect(Collectors.toList());
//        Page<VideoFileDAO> page = repository.findAllBySnapshotIsNull(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));
//        List<VideoFileDTO> videoFileDTOList = page.get()
//                .map(VideoFileDTO::new)
//                .collect(Collectors.toList());
//        return videoFileDTOList;
    }

    @Override
    public List<VideoFileDTO> findAllWithSnapshotLimitedBy(Integer limit) {
        if (limit == null) return repository
                .findAllBySnapshotIsNotNull()
                .stream()
                .map(VideoFileDTO::new)
                .collect(Collectors.toList());
        Page<VideoFileDAO> page = repository.findAllBySnapshotIsNotNull(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));
        List<VideoFileDTO> videoFileDTOList = page.get()
                .map(VideoFileDTO::new)
                .collect(Collectors.toList());
        return videoFileDTOList;
    }


    @Override
    public void save(VideoFileDTO file) {
        VideoFileDAO videoFileDAO = new VideoFileDAO(file);
        if (!repository.exists(Example.of(videoFileDAO)))
            repository.save(videoFileDAO);
    }

    @TrackTime
    @Override
    public void saveAll(Collection<VideoFileDTO> files) {


//        List<String> pathsOfExistingFiles = repository.findAll()
//                .stream()
//                .map(VideoFileDAO::getFilePath)
//                .collect(Collectors.toList());

//        List<String> pathsOfExistingFiles = repository.findAllBy(Sort.unsorted())
//                .stream()
//                .map(VideoFileDTO::getFilePath)
//                .collect(Collectors.toList());

        List<String> pathsOfExistingFiles = repository.findAllPaths();


        List<VideoFileDAO> filesToAdd =
                files.stream()
                        .sorted(Comparator.comparing(VideoFileDTO::getDate).reversed())
                        .limit(filesCountLimit != null ? filesCountLimit : Long.MAX_VALUE)
                        .filter(videoFileDTO -> !pathsOfExistingFiles.contains(videoFileDTO.getFilePath()))
                        .map(VideoFileDAO::new)
                        .collect(Collectors.toList());

        repository.saveAll(filesToAdd);
// TODO: 11.12.2019 DTO->DAO need to create Mapper
        if (filesCountLimit != null) {
//            List<VideoFileDAO> filesToDelete = repository.findAllByOrderByDateDesc().stream()
//                    .skip(filesCountLimit)
//                    .map(VideoFileDAO::new)
//                    .collect(Collectors.toList());
//
//            System.out.println("==================================");
//            System.out.println("filesToDelete count = " + filesToDelete.size());
//            System.out.println("==================================");
//            repository.deleteAll(filesToDelete);


            List<String> filePathsToDelete = repository.findAllByOrderByDateDesc().stream()
                    .skip(filesCountLimit)
                    .map(a -> a.getFilePath())
                    .collect(Collectors.toList());

            System.out.println("==================================");
            System.out.println("filePathsToDelete count = " + filePathsToDelete.size());
            System.out.println("==================================");

            filePathsToDelete.forEach(path -> repository.deleteByFilePath(path));
//            repository.deleteAllByFilePath(filePathsToDelete);

            // TODO: 11.12.2019 Mistake
//            javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call

        }

//        try {
//            repository.saveAll(files);
//
//
//        } catch (Exception e) {
//            for (VideoFile file : files) {
//                if (!repository.exists(Example.of(file)))
//                    repository.save(file);
//            }
//        }

//                repository.saveIgnore(file.getFullName(),file.getFilePath(),file.getSize(),file.getDate(),file.getCameraName(),file.getVideoType());


//      When  if (!repository.exists(Example.of(file)))
//                repository.save(file);
//        2019-11-25 13:29:06.258 DEBUG 3536 --- [nio-8080-exec-1] org.hibernate.SQL                        : select videofile0_.id as id1_0_, videofile0_.camera_name as camera_n2_0_, videofile0_.date as date3_0_, videofile0_.file_path as file_pat4_0_, videofile0_.full_name as full_nam5_0_, videofile0_.size as size6_0_, videofile0_.video_type as video_ty7_0_ from video_file videofile0_ where videofile0_.camera_name=? and videofile0_.date=? and videofile0_.file_path=? and videofile0_.size=684 and videofile0_.video_type=? and videofile0_.full_name=?
//        2019-11-25 13:29:06.260 DEBUG 3536 --- [nio-8080-exec-1] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        2019-11-25 13:29:06.307 DEBUG 3536 --- [nio-8080-exec-1] org.hibernate.SQL                        : select videofile0_.id as id1_0_, videofile0_.camera_name as camera_n2_0_, videofile0_.date as date3_0_, videofile0_.file_path as file_pat4_0_, videofile0_.full_name as full_nam5_0_, videofile0_.size as size6_0_, videofile0_.video_type as video_ty7_0_ from video_file videofile0_ where videofile0_.camera_name=? and videofile0_.date=? and videofile0_.file_path=? and videofile0_.size=114528 and videofile0_.video_type=? and videofile0_.full_name=?
//        2019-11-25 13:29:06.309 DEBUG 3536 --- [nio-8080-exec-1] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        Total time of saveAll 414301 ms
// IF ALL FILES EXIST
//        2019-11-25 13:39:43.962 DEBUG 3536 --- [nio-8080-exec-3] org.hibernate.SQL                        : select videofile0_.id as id1_0_, videofile0_.camera_name as camera_n2_0_, videofile0_.date as date3_0_, videofile0_.file_path as file_pat4_0_, videofile0_.full_name as full_nam5_0_, videofile0_.size as size6_0_, videofile0_.video_type as video_ty7_0_ from video_file videofile0_ where videofile0_.camera_name=? and videofile0_.date=? and videofile0_.file_path=? and videofile0_.size=684 and videofile0_.video_type=? and videofile0_.full_name=?
//        2019-11-25 13:39:43.965 DEBUG 3536 --- [nio-8080-exec-3] org.hibernate.SQL                        : select videofile0_.id as id1_0_, videofile0_.camera_name as camera_n2_0_, videofile0_.date as date3_0_, videofile0_.file_path as file_pat4_0_, videofile0_.full_name as full_nam5_0_, videofile0_.size as size6_0_, videofile0_.video_type as video_ty7_0_ from video_file videofile0_ where videofile0_.camera_name=? and videofile0_.date=? and videofile0_.file_path=? and videofile0_.size=684 and videofile0_.video_type=? and videofile0_.full_name=?
//        2019-11-25 13:39:43.968 DEBUG 3536 --- [nio-8080-exec-3] org.hibernate.SQL                        : select videofile0_.id as id1_0_, videofile0_.camera_name as camera_n2_0_, videofile0_.date as date3_0_, videofile0_.file_path as file_pat4_0_, videofile0_.full_name as full_nam5_0_, videofile0_.size as size6_0_, videofile0_.video_type as video_ty7_0_ from video_file videofile0_ where videofile0_.camera_name=? and videofile0_.date=? and videofile0_.file_path=? and videofile0_.size=114528 and videofile0_.video_type=? and videofile0_.full_name=?
//        Total time of saveAll 35561 ms


//        When repository.saveAll(files);
//        2019-11-25 13:43:24.930 DEBUG 12040 --- [nio-8080-exec-1] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        2019-11-25 13:43:24.931 DEBUG 12040 --- [nio-8080-exec-1] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        2019-11-25 13:43:24.931 DEBUG 12040 --- [nio-8080-exec-1] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        Total time of saveAll 8591 ms

//          When insert first time
//          try {
//                    repository.save(file);
//                } catch (Exception ignored) {
//                }
//        2019-11-25 14:46:37.052 DEBUG 6468 --- [nio-8080-exec-2] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        2019-11-25 14:46:37.120 DEBUG 6468 --- [nio-8080-exec-2] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        2019-11-25 14:46:37.170 DEBUG 6468 --- [nio-8080-exec-2] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        Total time of saveAll 462370 ms

//          When AllReady exists
//        2019-11-25 14:48:50.226 ERROR 6468 --- [nio-8080-exec-6] o.h.engine.jdbc.spi.SqlExceptionHelper   : ОШИБКА: повторяющееся значение ключа нарушает ограничение уникальности "uk_es1fnepmtiajr1m51cj99l81t"
//        Подробности: Ключ "(file_path)=(d:\Record\XML\2019112514FileXml.xml)" уже существует.
//        2019-11-25 14:48:50.227 DEBUG 6468 --- [nio-8080-exec-6] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)
//        2019-11-25 14:48:50.228  WARN 6468 --- [nio-8080-exec-6] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: 23505
//        2019-11-25 14:48:50.228 ERROR 6468 --- [nio-8080-exec-6] o.h.engine.jdbc.spi.SqlExceptionHelper   : ОШИБКА: повторяющееся значение ключа нарушает ограничение уникальности "uk_es1fnepmtiajr1m51cj99l81t"
//        Подробности: Ключ "(file_path)=(d:\Record\XML\ListManager.xml)" уже существует.
//        Total time of saveAll 24382 ms

//When using for first time
//        try {
//            repository.saveIgnore(file.getFullName(),file.getFilePath(),file.getSize(),file.getDate(),file.getCameraName(),file.getVideoType());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        2019-11-25 16:10:01.629 DEBUG 8080 --- [nio-8080-exec-1] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        Hibernate: insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        2019-11-25 16:10:01.630 DEBUG 8080 --- [nio-8080-exec-1] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        Hibernate: insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        Total time of saveAll 27461 ms

//If we have All the records
//        Hibernate: insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        2019-11-25 16:12:44.727 DEBUG 8080 --- [nio-8080-exec-6] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        Hibernate: insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        2019-11-25 16:12:44.728 DEBUG 8080 --- [nio-8080-exec-6] org.hibernate.SQL                        : insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        Hibernate: insert into video_file (camera_name, date, file_path, full_name, size, video_type) values (?, ?, ?, ?, ?, ?)  on conflict do nothing
//        Total time of saveAll 14654 ms
        //THIS VARIANT PRODUCES INCREMENT OF IDs


    }


    //    @PostConstruct
    private void InsertDummyUser() {
        String fullName = "20191123_123658";
        String filePath = "c:\\Mod\\192.168.1.11\\1\\20191123_123658.h264";
        Long size = 10_944_512L;
        Date date = new Date();
        String cameraName = "192.168.1.11";
        String videoType = "Mod";
        VideoFileDAO videoFileDAO = new VideoFileDAO(fullName, filePath, size, date, cameraName, videoType);

        repository.save(videoFileDAO);
    }

    @Override
    public void fakeCreateSnapshots() {
        for (VideoFileDAO fileDAO : repository.findAllBySnapshotIsNull()) {
            String filePath = fileDAO.getFilePath();

            Path snapshotPath = Paths.get(filePath.replace(".h264", ".jpg"));
            System.out.println(snapshotPath);
            if (Files.exists(snapshotPath) && Files.isRegularFile(snapshotPath)) {
                try {
                    byte[] snapshotContent = Files.readAllBytes(snapshotPath);
                    System.out.println("snapshotContent.length = " + snapshotContent.length);
                    fileDAO.setSnapshot(snapshotContent);
                    repository.save(fileDAO);

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }


        }

    }

    @Override
    public byte[] findSnapshotByVideoFilePath(String videoFilePath) {
        VideoFileDAO videoFileDAO = repository.findOneByFilePath(videoFilePath);
        return videoFileDAO.getSnapshot();
    }

    @Override
    public void store(MultipartFile file, String fullFilePath) {


        String originalFilename = (fullFilePath == null || fullFilePath.isEmpty()) ?
                file.getOriginalFilename() : fullFilePath;
//        String filename = StringUtils.cleanPath(originalFilename);
        String filename = originalFilename;

        System.out.println(filename);
        filename = filename.replace(".jpg", ".h264");
        System.out.println(filename);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

            byte[] fileContent = file.getBytes();
            VideoFileDAO videoFileDAO = repository.findOneByFilePath(filename);
            if (videoFileDAO == null)
                videoFileDAO = repository.findOneByFilePathContains(filename);
            if (videoFileDAO == null) return;
            System.out.println(videoFileDAO.getFilePath());
            videoFileDAO.setSnapshot(fileContent);
            repository.save(videoFileDAO);

        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

    }
}
