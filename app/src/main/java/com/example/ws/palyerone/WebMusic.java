package com.example.ws.palyerone;

import java.io.Serializable;

public class WebMusic implements Serializable {
    private String musicId;
    private String musicName;
    private String musicTitle;
    private String musicPath;
    private String musicSinger;
    private String musicImage;

    public WebMusic(String id,String name,String path,String singer,String image,String title){
        this.musicSinger = singer;
        this.musicImage = image;
        this.musicTitle = title;
        this.musicPath = path;
        this.musicName = name;
        this.musicId = id;

    }

    public void setMusicId(String id){
        this.musicId = id;
    }

    public String getMusicId(){
        return musicId;
    }

    public void setMusicSinger(String singer){
        this.musicSinger = singer;
    }

    public String getMusicSinger(){
        return musicSinger;
    }

    public void setMusicTitle(String title){
        this.musicTitle = title;
    }

    public String getMusicTitle(){
        return musicTitle;
    }




    public void setMusicImage(String image){
        this.musicImage = image;
    }

    public String getMusicImage(){
        return musicImage;
    }


    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }


    public String getMusicName() {

        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
}
