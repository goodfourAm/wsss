package com.example.ws.palyerone;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WebMusicUtil {
    private static List<WebMusic> webMusicList= new ArrayList<>();
    private static String CLOUD_MUSIC_API_MUSIC = "http://music.163.com/song/media/outer/url?id=";

    //json解析网络响应
    public static List<WebMusic> parseResponse(String data) {
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
        return webMusicList;
    }
}
