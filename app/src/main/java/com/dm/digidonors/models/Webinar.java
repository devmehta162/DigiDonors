package com.dm.digidonors.models;

public class Webinar {

    private String title;
    private String description;
    private String date;
    private String startTime;
    private String fee;
    private String benefits;
    private String link;
    private String webinarId;
    private String userId;
    private String endTime;
    private String duration;

    public Webinar(String title, String description, String date, String startTime, String fee, String benefits, String link, String webinarId, String userId, String endTime, String duration) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.fee = fee;
        this.benefits = benefits;
        this.link = link;
        this.webinarId = webinarId;
        this.userId = userId;
        this.endTime = endTime;
        this.duration = duration;
    }

    public Webinar() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }



    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


    public String getEndTime() {
        return endTime;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getWebinarId() {
        return webinarId;
    }

    public void setWebinarId(String webinarId) {
        this.webinarId = webinarId;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

