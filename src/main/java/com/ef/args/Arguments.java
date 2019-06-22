package com.ef.args;

import java.time.ZonedDateTime;

public class Arguments {
    private Duration duration;
    private ZonedDateTime startDate;
    private Integer threshold;
    private String accessLogFileLocation;

    public Arguments(Duration duration, ZonedDateTime startDate, Integer threshold, String accessLogFileLocation) {
        this.duration = duration;
        this.startDate = startDate;
        this.threshold = threshold;
        this.accessLogFileLocation = accessLogFileLocation;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getAccessLogFileLocation() {
        return accessLogFileLocation;
    }

    public void setAccessLogFileLocation(String accessLogFileLocation) {
        this.accessLogFileLocation = accessLogFileLocation;
    }
}
