package com.mabrouk.model;

public class Program {

    private String time;
    private String timeType;
    private String title;
    private String subTitle;
    private String link;

    public Program(String time, String timeType, String title, String subTitle, String link) {
        this.time = time.equals("") ? null : time;
        this.timeType = timeType.equals("") ? null : timeType;
        this.title = title.equals("") ? null : title;
        this.subTitle = subTitle.equals("") ? null : subTitle;
        this.link = link.equals("") ? null : link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
