package services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.ws.palyerone.DisplayActivity;
import com.example.ws.palyerone.Music;
import com.example.ws.palyerone.MyApplication;
import com.example.ws.palyerone.Util;
import com.example.ws.palyerone.WebMusic;
import com.example.ws.palyerone.adapter.RecyclerViewAdapter;
import com.example.ws.palyerone.fragment.FragmentOne;

public class MusicPlayerService extends Service {

    private static MediaPlayer mediaPlayer;
    private static Music current_music;
    private static WebMusic webMusic;
    private int positon = -1;
    private int dif = -1;
//    private int finash = 0;

    public static class MusicController extends Binder {

        public boolean getMusicPlaying(){
            if(mediaPlayer.isPlaying()){
                return true;
            }else
                return false;
        }
        public long getMusicDuration() {
            if(mediaPlayer != null){
                return mediaPlayer.getDuration();//获取文件的总长度
            }else {
                return 100;
            }

        }

        public long getPosition() {
            if (mediaPlayer != null){
                return mediaPlayer.getCurrentPosition();//获取当前播放进度
            }else {
                return 0;
            }

        }

        public void setPosition (int position) {
            if(mediaPlayer != null){
                mediaPlayer.seekTo(position);//重新设定播放进度
            }
        }
    }



    int num;
    public MusicPlayerService(){
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicController();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Bundle bundle = intent.getExtras();
        dif = bundle.getInt("dif");

        if (dif == -1) {
            num = bundle.getInt("num");
            if(num == 1){
                    positon = bundle.getInt("position");
                    current_music = Util.scanAllAudioFiles().get(positon);
                    initMediaPlayer();
                    play();
            }else if(num == 2){
                if (current_music == null){
                    positon = bundle.getInt("position");
                    current_music = Util.scanAllAudioFiles().get(positon);
                    initMediaPlayer();
                    play();
                } else {
                    pause();
                }
            }
        } else if (dif == 1) {
            int num = bundle.getInt("num");
            if (num == 1) {
                positon = bundle.getInt("position");
                webMusic = FragmentOne.getWebMusic().get(positon);
                webinitMediaPlayer();
                play();
            } else if (num == 2) {
                pause();
            } else if (num == 3) {
                positon++;
                if (positon >= FragmentOne.getWebMusic().size()) {
                    positon = 0;
                }
                webMusic = FragmentOne.getWebMusic().get(positon);
                webinitMediaPlayer();
                play();
            }
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
               positon++;
                if(positon >= Util.scanAllAudioFiles().size()){
                    positon = 0;
                }
                current_music = Util.scanAllAudioFiles().get(positon);
                initMediaPlayer();
                play();
                chuanDi();
            }
        });
        chuanDi();
        return super.onStartCommand(intent, flags, startId);
    }

    private void webinitMediaPlayer(){
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(webMusic.getMusicPath());
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void  chuanDi(){
        final Intent in =new Intent();
        in.putExtra("count", positon);
        if(mediaPlayer.isPlaying()){
            int play = 1;
            in.putExtra("play",play);
        }else {
            int play = 2;
            in.putExtra("play",play);
        }
        in.putExtra("dif",dif);
        in.setAction("com.ljq.activity.CountService");
        sendBroadcast(in);
    }
    @Override
    public void onCreate() {
        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }


    }

    @Override
    public void onDestroy() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }




    private void play(){
        mediaPlayer.start();
    }

    public void pause(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else {
            mediaPlayer.start();
        }
    }
    private void initMediaPlayer(){
        try {
//            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(current_music.getUrl());
                mediaPlayer.prepare();
//            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static int getDuration(){
        if(mediaPlayer == null){
            return 100*1000;
        }else {
            return  (int) current_music.getDuration();
        }
    }
    public static int getCurrenposition(){
        if(mediaPlayer == null){
            return 0;
        }else {
            return mediaPlayer.getCurrentPosition();
        }
    }

}
