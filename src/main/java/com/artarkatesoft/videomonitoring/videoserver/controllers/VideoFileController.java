package com.artarkatesoft.videomonitoring.videoserver.controllers;

import com.artarkatesoft.videomonitoring.videoserver.dao.VideoFileDAOwoSnapshotProjection;
import com.artarkatesoft.videomonitoring.videoserver.dto.VideoFileDTO;
import com.artarkatesoft.videomonitoring.videoserver.services.VideoFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class VideoFileController {

    @Autowired
    private VideoFileService videoFileService;

    @GetMapping("/api/videos")
    @ResponseBody
    public List<VideoFileDTO> getAllVideoFilesAPI(@RequestParam(name = "limit", required = false) Integer limit) {
        long start = System.currentTimeMillis();

        List<VideoFileDTO> allFiles = limit == null ? videoFileService.findAll() : videoFileService.findAllLimitedBy(limit);

        System.out.printf(" videoFileService.findAll() takes %d ms\n", System.currentTimeMillis() - start);

        int size = allFiles.size();

        System.out.printf("Size of videoFileService.findAll(): %d\n", size);

        return allFiles;
//        if (limit == null) return allFiles;
//        return allFiles.stream().limit(limit).collect(Collectors.toList());
    }

    @GetMapping("/api/videos/without_snapshot")
    @ResponseBody
    public List<VideoFileDTO> getAllVideoFilesWithoutSnapshotsAPI(@RequestParam(name = "limit", required = false) Integer limit) {
        return videoFileService.findAllWithoutSnapshotLimitedBy(limit);
    }

    @GetMapping("/api/videos/with_snapshot")
    @ResponseBody
    public List<VideoFileDTO> getAllVideoFilesWithSnapshotsAPI(@RequestParam(name = "limit", required = false) Integer limit) {
        return videoFileService.findAllWithSnapshotLimitedBy(limit);
    }

    @GetMapping("/videos")
    public String getAllVideoFiles(Model model, @RequestParam(name = "limit", required = false) Integer limit) {
        long start = System.currentTimeMillis();
//        List<VideoFile> allFiles = videoFileService.findAll();
        List<VideoFileDTO> allFiles = limit == null ?
                videoFileService.findAll() :
                videoFileService.findAllLimitedBy(limit);

        System.out.printf(" videoFileService.findAll() takes %d ms\n", System.currentTimeMillis() - start);
        int size = allFiles.size();
        System.out.printf("Size of videoFileService.findAll(): %d\n", size);
        model.addAttribute("videoFilesFromController", allFiles);


//        return allFiles.stream().limit(limit).collect(Collectors.toList());
        return "videos_page";
    }

    @GetMapping("/videos/snapshots")
    public String getAllSnapshots(Model model, @RequestParam(name = "limit", required = false) Integer limit) {
        long start = System.currentTimeMillis();
//        List<VideoFile> allFiles = videoFileService.findAll();
        List<VideoFileDTO> allFiles = limit == null ?
                videoFileService.findAll() :
                videoFileService.findAllLimitedBy(limit);


        System.out.printf(" videoFileService.findAll() takes %d ms\n", System.currentTimeMillis() - start);
        int size = allFiles.size();
        System.out.printf("Size of videoFileService.findAll(): %d\n", size);
        model.addAttribute("videoFilesFromController", allFiles);


//        return allFiles.stream().limit(limit).collect(Collectors.toList());
        return "snapshots_page";
    }

    @GetMapping("/videos/snapshots/{cameraName}")
    public String getCameraSnapshots(Model model, @RequestParam(name = "limit", required = false) Integer limit, @PathVariable String cameraName) {

//        List<VideoFileDTO> allFiles = limit == null ?
//                videoFileService.findAll() :
//                videoFileService.findAllLimitedBy(limit);


 List<VideoFileDAOwoSnapshotProjection> allFiles =
                videoFileService.findAllByCameraNameLimitBy(cameraName, limit);
//List<VideoFileDTO> allFiles =
//                videoFileService.findAllByCameraNameLimitBy(cameraName, limit);


        model.addAttribute("videoFilesFromController", allFiles);


//        return allFiles.stream().limit(limit).collect(Collectors.toList());
        return "snapshots_page";
    }


    @PostMapping("/api/videos")
    @ResponseBody
    public ResponseEntity<Object> addAllVideoFiles(@RequestBody List<VideoFileDTO> videoFileDTOList) {
        videoFileService.saveAll(videoFileDTOList);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/videos/add")
    @ResponseBody
    public ResponseEntity<Object> addVideoFile(@RequestBody VideoFileDTO videoFileDTO) {
        videoFileService.save(videoFileDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/videos/snapshot")
    @ResponseBody
    public ResponseEntity<Object> addSnapshot(@RequestParam("file") MultipartFile file, @RequestParam(value = "full_file_path", required = false) String fullFilePath/*,
                                              RedirectAttributes redirectAttributes*/) {

        videoFileService.store(file, fullFilePath);
        return ResponseEntity.ok().build();

//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:/";
    }


    @GetMapping(value = "/videos/snapshot", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getSnapshot(@RequestParam(name = "video_file_path") String videoFilePath) throws IOException {
        byte[] snapshot = videoFileService.findSnapshotByVideoFilePath(videoFilePath);
        if (snapshot == null) return ResponseEntity.notFound().build();
        ByteArrayInputStream bais = new ByteArrayInputStream(snapshot);
        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);
        return ResponseEntity
                .ok()
                .cacheControl(cacheControl)
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(bais));
    }
// @GetMapping(value = "/videos/snapshot", produces = MediaType.IMAGE_JPEG_VALUE)
//    @ResponseBody
//    public ResponseEntity<InputStreamResource> getSnapshot(@RequestParam(name = "filename", required = false) String fileName) throws IOException {
//        ClassPathResource imgFile = new ClassPathResource("image/20191123_184245.jpg");
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(new InputStreamResource(imgFile.getInputStream()));
//    }

    @GetMapping(value = "/videos/snapshot/make")
    @ResponseBody
    public ResponseEntity<Object> fillDatabaseWithSnapshots(@RequestParam(name = "filename", required = false) String fileName) throws IOException {

        videoFileService.fakeCreateSnapshots();
        return ResponseEntity
                .ok()
                .build();
    }


}
