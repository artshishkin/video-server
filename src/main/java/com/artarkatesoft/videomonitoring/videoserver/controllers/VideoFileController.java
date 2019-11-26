package com.artarkatesoft.videomonitoring.videoserver.controllers;

import com.artarkatesoft.videomonitoring.videoserver.model.VideoFile;
import com.artarkatesoft.videomonitoring.videoserver.services.VideoFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VideoFileController {

    @Autowired
    private VideoFileService videoFileService;

    @GetMapping("/videos")
    @ResponseBody
    public List<VideoFile> getAllVideoFiles(@RequestParam(name = "limit", required = false) Long limit) {
        long start = System.currentTimeMillis();
        List<VideoFile> allFiles = videoFileService.findAll();
        System.out.printf(" videoFileService.findAll() takes %d ms\n", System.currentTimeMillis() - start);
        int size = allFiles.size();
        System.out.printf("Size of videoFileService.findAll(): %d\n", size);
        if (limit == null) return allFiles;
        return allFiles.stream().limit(limit).collect(Collectors.toList());
    }

    @PostMapping("/videos")
    @ResponseBody
    public ResponseEntity<Object> addAllVideoFiles(@RequestBody List<VideoFile> videoFiles) {
        videoFileService.saveAll(videoFiles);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/videos/add")
    @ResponseBody
    public String addVideoFile(@RequestBody VideoFile videoFile) {
//        System.out.println(videoFile);
        videoFileService.save(videoFile);


        return "OK";
    }
//public ResponseEntity<Object> addVideoFile(@RequestBody VideoFile videoFile) {
//        System.out.println(videoFile);
//        videoFileService.save(videoFile);
//
//
//        return ResponseEntity.ok().build();
//    }


}
