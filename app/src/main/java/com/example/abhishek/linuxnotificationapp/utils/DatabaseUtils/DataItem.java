package com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils;

public class DataItem {

    private String title, body, datetime;
    private int id;

    public DataItem(String title, String body, String datetime, int id) {
        this.title = title;
        this.body = body;
        this.datetime = datetime;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", datetime='" + datetime + '\'' +
                ", id=" + id +
                '}';
    }
}
