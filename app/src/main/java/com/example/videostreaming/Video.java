package com.example.videostreaming;

/**
 * Created by Mook on 17/08/2016.
 */

public class Video {
    private String name,vdourl;
    private int thumbnail;

    public Video() {}

    public Video(String name, int thumbnail, String vdourl) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.vdourl = vdourl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl(){
        return vdourl;
    }
}
