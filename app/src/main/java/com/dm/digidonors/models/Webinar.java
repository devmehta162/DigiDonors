package com.dm.digidonors.models;

public class Webinar {

    private String title;
    private String description;
    private String date;
    private String starttime;
    private String endtime;
    private String fee;
    private String benefits;
    private String link;
    private String webinarId;
    private String userId;

    public Webinar(String title, String description, String date, String starttime, String endtime, String fee, String benefits, String link, String webinarId, String userId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.fee = fee;
        this.benefits = benefits;
        this.link = link;
        this.webinarId = webinarId;
        this.userId = userId;
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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
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

