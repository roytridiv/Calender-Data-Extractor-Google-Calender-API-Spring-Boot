package com.tridiv.demo.Domain;

public class EventInfo {
    private String startTime;
    private  String endTime;
    private String description;
    private String summary;
    private Long duration;


    public EventInfo(String startTime, String endTime, String description, String summary, Long duration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.summary = summary;
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

}
