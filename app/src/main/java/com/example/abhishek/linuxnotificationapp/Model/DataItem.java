package com.example.abhishek.linuxnotificationapp.Model;

import java.util.Date;

public class DataItem {
    private Date date;
    private String title, body;

    public DataItem() {
    }

    public DataItem(Date date, String title, String body) {
        this.date = date;
        this.title = title;
        this.body = body;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
