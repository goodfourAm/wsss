package com.example.ws.palyerone.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ws.palyerone.OkHttp;
import com.example.ws.palyerone.R;
import com.example.ws.palyerone.Util;
import com.example.ws.palyerone.WebMusic;
import com.example.ws.palyerone.adapter.RecyclerViewAdapter;
import com.example.ws.palyerone.adapter.WebMusicAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by ws on 2018/4/17.
 */

public class FragmentOne extends Fragment implements View.OnClickListener {

    private EditText editText;
    private ImageButton imageButton;
//    private TextView textView;
    private String data;
    private RecyclerView recyclerView;
    private  static List<WebMusic> webMusicList= new ArrayList<>();
    private String CLOUD_MUSIC_API_MUSIC = "http://music.163.com/song/media/outer/url?id=";
    private WebMusicAdapter adapter;

    public FragmentOne() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_168:
                String key = editText.getText().toString();
                String url ="http://s.music.163.com/search/get/?type=1&s=" + key + "&limit=20&offset=0";
                OkHttp.sendOkHttp(url, new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {

                        data = response.body().string();


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(layoutManager);
                                adapter = new WebMusicAdapter(webMusicList);
                                recyclerView.setAdapter(adapter);
                                parseResponse();
                            }
                        });
                    }
                });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmentone, container, false);
        editText = (EditText) view.findViewById(R.id.sou_name);
        imageButton = (ImageButton) view.findViewById(R.id.go_168);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_viewOne);

//        textView = (TextView) view.findViewById(R.id.view);
        imageButton.setOnClickListener(this);




        return view;
    }
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        if(mediaPlayer != null){
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//    }

    //json解析网络响应
    private void parseResponse() {
        try {
            JSONObject response = new JSONObject(data);
            JSONObject result = response.getJSONObject("result");
            JSONArray songs = result.getJSONArray("songs");
            if (webMusicList.size() > 0) webMusicList.clear();
            for (int i = 0; i < songs.length(); i++) {
                JSONObject song = songs.getJSONObject(i);
                //获取歌曲名字
                String id = song.getString("id");
                String name = song.getString("name");
                String title = song.getJSONObject("album").getString(
                        "name");
                //获取歌词演唱者
                String artist = song.getJSONArray("artists")
                        .getJSONObject(0).getString("name");
                //获取歌曲专辑图片的url
                String albumPicUrl = song.getJSONObject("album").getString(
                        "picUrl");
                //获取歌曲音频的url
                String audioUrl = CLOUD_MUSIC_API_MUSIC + id + ".mp3";
                Log.d(TAG, "doenloadUrl = " + audioUrl);
                //保存音乐信息的Map
                WebMusic music = new WebMusic(id,name,audioUrl,artist,albumPicUrl,title);
//                    Map<String, Object> item = new HashMap<>();
//                    item.put("title", title);
//                    item.put("artist", artist);
//                    item.put("picUrl", albumPicUrl);
//                    picUrlMap.put(title + artist, new SoftReference<String>(
//                            albumPicUrl));
//                    item.put("audio", audioUrl);
                //将一条歌曲信息存入list中
                webMusicList.add(music);
            }
            Log.d(TAG, "搜到" + webMusicList.size() + "首歌");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static List<WebMusic> getWebMusic(){
        return webMusicList;
    }



}


