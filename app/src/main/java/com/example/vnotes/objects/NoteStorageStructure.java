package com.example.vnotes.objects;

import java.lang.Math;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteStorageStructure {
    private Integer key;
    private String title;
    private String date;
    private String hour;
    private String content;

    public NoteStorageStructure() {
        this.key = (int) (Math.random() * 689379863) + 1;
        this.title = "No title";
        this.date= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.hour = new SimpleDateFormat("HH:mm").format(new Date());
        this.content = "";
    }

    public NoteStorageStructure(String title, String date, String hour, String content) {
        this.key = (int) (Math.random() * 689379863) + 1;
        this.title = title;
        this.date = date;
        this.hour = hour;
        this.content = content;
    }

    public int getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null || title.isEmpty()) {
            this.title = "No title";
        }
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(NoteStorageStructure obj) {
        return this.key == obj.getKey();
    }

    @Override
    public String toString() {
        return "NoteStorageStructure{" +
                "key=" + key +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
