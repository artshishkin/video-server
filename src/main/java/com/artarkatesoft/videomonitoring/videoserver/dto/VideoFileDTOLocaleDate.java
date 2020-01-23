package com.artarkatesoft.videomonitoring.videoserver.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

public class VideoFileDTOLocaleDate extends VideoFileDTO {
    private Locale locale;
    private DateTimeFormatter f = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);


    public void setLocale(Locale locale) {
        this.locale = locale;
        f = f.withLocale(locale);
    }

    public String getFormattedDate() {
//        DateFormat df = DateFormat.getDateTimeInstance(
//                DateFormat.LONG,
//                DateFormat.LONG,
//                locale
//        );
        ZonedDateTime zdt = getDate().toInstant().atZone(TimeZone.getTimeZone("EET").toZoneId());
        return zdt.format(f);
//        return df.format(getDate());
    }
}
