package com.artarkatesoft.videomonitoring.videoserver.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public interface VideoFileDAOwoSnapshotProjection {

    String getFileName();

    String getFilePath();

    Long getSize();

    Date getDate();

    String getCameraName();

    String getVideoType();

    default String getFilePathEncoded() throws UnsupportedEncodingException {
        return URLEncoder.encode(getFilePath(), StandardCharsets.UTF_8.toString());
    }


}
