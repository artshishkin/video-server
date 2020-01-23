package com.artarkatesoft.videomonitoring.videoserver.dto;

import java.text.DateFormat;
import java.util.Locale;

public class VideoFileDTOLocaleDate extends VideoFileDTO{
    private Locale locale;

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getFormattedDate(){
        DateFormat df = DateFormat.getDateTimeInstance(
                DateFormat.LONG,
                DateFormat.LONG,
                locale
        );

        return df.format(getDate());
    }
}
