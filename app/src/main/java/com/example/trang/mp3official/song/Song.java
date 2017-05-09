package com.example.trang.mp3official.song;

import android.net.Uri;

/**
 * Created by Trang on 4/18/2017.
 */

public class Song {
    private String titel;
    private int index;
    private String author;
    private Uri data;
    private long duration;
    private String content;

    public Song(String titel, int index, String author, Uri data, long duration) {
        this.titel = titel;
        this.index = index;
        this.author = author;
        this.data = data;
        this.duration = duration;
    }

    public Song(String titel, int index, String author, Uri data, long duration, String content) {
        this.titel = titel;
        this.index = index;
        this.author = author;
        this.data = data;
        this.duration = duration;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Song() {
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Uri getData() {
        return data;
    }

    public void setData(Uri data) {
        this.data = data;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
