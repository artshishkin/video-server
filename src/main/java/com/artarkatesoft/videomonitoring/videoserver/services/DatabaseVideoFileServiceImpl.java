package com.artarkatesoft.videomonitoring.videoserver.services;

import com.artarkatesoft.videomonitoring.videoserver.model.VideoFile;
import com.artarkatesoft.videomonitoring.videoserver.repository.VideoFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseVideoFileServiceImpl implements VideoFileService {

    @Autowired
    private VideoFileRepository repository;

    @Override
    public List<VideoFile> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    @Override
    public List<VideoFile> findAllLimitedBy(int limit) {

        Page<VideoFile> page = repository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date")));
        List<VideoFile> videoFiles = page.get().collect(Collectors.toList());
        return videoFiles;
//        return repository.findTop10ByOrderByDateDesc();
    }


    @Override
    public void save(VideoFile file) {
        if (!repository.exists(Example.of(file)))
            repository.save(file);
    }

    @Override
    public void saveAll(Collection<VideoFile> files) {
        long start = System.currentTimeMillis();


        List<String> pathsOfExistingFiles = repository.findAll()
                .stream()
                .map(VideoFile::getFilePath)
                .collect(Collectors.toList());

        List<VideoFile> filesToAdd = files.stream()
                .filter(videoFile -> !pathsOfExistingFiles.contains(videoFile.getFilePath()))
                .collect(Collectors.toList());
        repository.saveAll(filesToAdd);

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

        System.out.printf("Total time of saveAll %d ms\n", System.currentTimeMillis() - start);

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
        VideoFile videoFile = new VideoFile(fullName, filePath, size, date, cameraName, videoType);

        repository.save(videoFile);
    }
}
