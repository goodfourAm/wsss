package com.example.ws.palyerone;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MusicRecyclerViewActivity extends AppCompatActivity {

//    private List<Fruit> fruitList = new ArrayList<>();
//    ArrayList<Music> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_recycler_view);

//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.musicRecycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(scanAllAudioFiles());
//        recyclerView.setAdapter(adapter);
    }

//    public  ArrayList<Music> scanAllAudioFiles()  {
//        musicList = new ArrayList<Music>();
//        Cursor cursor = getBaseContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
//        try {
//            if(cursor.moveToFirst()){
//                while(!cursor.isAfterLast()){
//                    //歌曲名
//                    String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
//                    String musicName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//                    //歌手名
//                    String author = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//                    //歌曲大小 /K
//                    Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
//                    String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//                    if(size>1024*800){
//                        Music music = new Music();
//                        music.setName(musicName);
//                        music.setAuthor(author);
//                        music.setUrl(url);
//                        musicList.add(music);
//
//                    }
//                    cursor.moveToNext();
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            cursor.close();
//        }
//
//        return musicList;
//    }

//    public static void runActivity(Context context, Music music) {
//
//        Intent intent = new Intent(context, DisplayActivity.class);
//        intent.putExtra("currentMusic", music);
//        context.startActivity(intent);
//    }
}
