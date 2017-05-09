package com.example.trang.mp3official.img;

/**
 * Created by Trang on 4/21/2017.
 */

public class Image {
    private int img;
    private String name;

    public Image(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public Image() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
