package com.example.ws.palyerone;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ws on 2018/4/22.
 */

public class Music implements Serializable {
    private String name;
    private String author;
    private String url;
    private long duration;
    public Music(){
        this.name = name;
        this.author = author;
        this.url = url;
        this.duration = duration;
//        this.artPic = artPic;
    }
    public  String  getName(){
        return name;
    }
    public String getAuthor(){
        return author;
    }
    public String getUrl(){
        return url;
    }
    public long getDuration(){
        return duration;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDuration(long duration){
        this.duration = duration;
    }
//    public Bitmap getArtPic(){
//        return artPic;
//    }
//    public void setArtPic(Bitmap artPic){
//        this.artPic = artPic;
//    }
}
