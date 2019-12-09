package com.artarkatesoft.videomonitoring.videoserver.dto;

import com.artarkatesoft.videomonitoring.videoserver.dao.VideoFileDAO;
import com.artarkatesoft.videomonitoring.videoserver.dao.VideoFileDAOwoSnapshotProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoFileDTO {

//    private Long id;

    private String fileName;
    private String filePath;
    private Long size;

    private Date date;

    private String cameraName;

    private String videoType;


//    public VideoFileDTO(String fileName, String filePath, Long size, Date date, String cameraName, String videoType) {
//        this.fileName = fileName;
//        this.filePath = filePath;
//        this.size = size;
//        this.date = date;
//        this.cameraName = cameraName;
//        this.videoType = videoType;
//    }

    public VideoFileDTO(VideoFileDAO dao) {
        this.fileName = dao.getFileName();
        this.filePath = dao.getFilePath();
        this.size = dao.getSize();
        this.date = dao.getDate();
        this.cameraName = dao.getCameraName();
        this.videoType = dao.getVideoType();

    }

    public VideoFileDTO(VideoFileDAOwoSnapshotProjection dao) {
        this.fileName = dao.getFileName();
        this.filePath = dao.getFilePath();
        this.size = dao.getSize();
        this.date = dao.getDate();
        this.cameraName = dao.getCameraName();
        this.videoType = dao.getVideoType();
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFilePathEncoded() {
        try {
            String filePathEncoded = URLEncoder.encode(filePath, StandardCharsets.UTF_8.toString());
            return filePathEncoded;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return filePath;
    }


    public Long getSize() {
        return size;
    }

    public Date getDate() {
        return date;
    }

    public String getCameraName() {
        return cameraName;
    }

    public String getVideoType() {
        return videoType;
    }
}
