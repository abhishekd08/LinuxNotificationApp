package com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

import static com.example.abhishek.linuxnotificationapp.utils.DatabaseUtils.Model.TABLE_NAME;

@Entity(tableName = TABLE_NAME)
public class Model {

    public static final String TABLE_NAME = "notifications";
    public static final String TITLE = "notification_title";
    public static final String BODY = "notification_body";
    public static final String DATETIME = "notificationdatetime";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "body")
    private String body;

    @ColumnInfo(name = "datetime")
    private String datetime;

    public Model() {
    }

    public Model(String title, String body, String datetime, int id) {
        this.title = title;
        this.body = body;
        this.datetime = datetime;
        this.id = id;
    }

    public Model(String title, String body, String datetime) {
        this.title = title;
        this.body = body;
        this.datetime = datetime;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }
}
