package services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

import com.example.ws.palyerone.DisplayActivity;
import com.example.ws.palyerone.Music;
import com.example.ws.palyerone.MyApplication;
import com.example.ws.palyerone.Util;
import com.example.ws.palyerone.WebMusic;
import com.example.ws.palyerone.fragment.FragmentOne;

import java.io.IOException;

public class WebMusicPlayerService extends Service {
    private MediaPlayer webMediaPlayer;
    private static WebMusic current_music;
    private int positon = -1;
    private String path;
    private int dif = 1;
    public WebMusicPlayerService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        Intent stopService = new Intent(MyApplication.getContext(),MusicPlayerService.class);
//        MyApplication.getContext().stopService(stopService);
        Bundle bundle = intent.getExtras();
        int num = bundle.getInt("num");
        if (num == 1) {
            positon = bundle.getInt("position");
            current_music = FragmentOne.getWebMusic().get(positon);
            initMediaPlayer();
            play();
        } else if (num == 2) {
            pause();
        } else if (num == 3) {
            positon ++;
            if (positon >= FragmentOne.getWebMusic().size()) {
                positon = 0;
            }
            current_music = FragmentOne.getWebMusic().get(positon);
            initMediaPlayer();
            play();
        }

        chuanDi();


        return super.onStartCommand(intent, flags, startId);
    }
//    private void initMediaPlayer() {
//        webMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        try {
//
//                    String urlPath = current_music.getMusicPath();
//                    if(webMediaPlayer.isPlaying()){
//
//                    }
//                    webMediaPlayer.setDataSource(urlPath);
//                    webMediaPlayer.prepare();
//                    webMediaPlayer.start();
//        }catch (IOException e) {
//                    e.printStackTrace();
//        }
//    }
    private void initMediaPlayer(){
        try {
                webMediaPlayer.reset();
                webMediaPlayer.setDataSource(current_music.getMusicPath());
                webMediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void play(){
        if (!webMediaPlayer.isPlaying()){
            webMediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webMediaPlayer.stop();
        webMediaPlayer.release();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(webMediaPlayer == null){
            webMediaPlayer = new MediaPlayer();
            webMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }
    public void  chuanDi(){
        final Intent in =new Intent();
        in.putExtra("count", positon);
        if(webMediaPlayer.isPlaying()){
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
    public void pause(){
        if (webMediaPlayer != null && webMediaPlayer.isPlaying()){
            webMediaPlayer.pause();
        }else {
            webMediaPlayer.start();
        }
    }
}
