package com.artarkatesoft.videomonitoring.videoserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // TODO: 28.11.2019 Refactor - change fullName to fileName
    private String fileName;

    @Column(unique = true)
    private String filePath;
    private Long size;

    private Date date;

    private String cameraName;

    private String videoType;

    public VideoFile(String fileName, String filePath, Long size, Date date, String cameraName, String videoType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.size = size;
        this.date = date;
        this.cameraName = cameraName;
        this.videoType = videoType;
    }


    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
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
