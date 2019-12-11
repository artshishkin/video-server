package com.artarkatesoft.videomonitoring.videoserver.dao;

import com.artarkatesoft.videomonitoring.videoserver.dto.VideoFileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "video_file")
public class VideoFileDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Column(unique = true)
    private String filePath;
    private Long size;

    private Date date;

    private String cameraName;

    private String videoType;

    private byte[] snapshot;

    public VideoFileDAO(String fileName, String filePath, Long size, Date date, String cameraName, String videoType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.size = size;
        this.date = date;
        this.cameraName = cameraName;
        this.videoType = videoType;
    }

    public VideoFileDAO(VideoFileDTO dto) {
        this.fileName = dto.getFileName();
        this.filePath = dto.getFilePath();
        this.size = dto.getSize();
        this.date = dto.getDate();
        this.cameraName = dto.getCameraName();
        this.videoType = dto.getVideoType();

    }

    public VideoFileDAO(VideoFileDAOwoSnapshotProjection dto) {
        this.fileName = dto.getFileName();
        this.filePath = dto.getFilePath();
        this.size = dto.getSize();
        this.date = dto.getDate();
        this.cameraName = dto.getCameraName();
        this.videoType = dto.getVideoType();

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

    public byte[] getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(byte[] snapshot) {
        this.snapshot = snapshot;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoFileDAO that = (VideoFileDAO) o;
        return fileName.equals(that.fileName) &&
                filePath.equals(that.filePath) &&
                size.equals(that.size) &&
                date.equals(that.date) &&
                cameraName.equals(that.cameraName) &&
                videoType.equals(that.videoType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, filePath, size, date, cameraName, videoType);
    }
}
