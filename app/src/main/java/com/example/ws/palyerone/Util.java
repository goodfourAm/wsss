package com.example.ws.palyerone;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.ws.palyerone.MyApplication.getContext;

/**
 * Created by ws on 2018/5/6.
 */

public class Util {

    private static ArrayList<Music> musicList = null;

    public static ArrayList<Music> scanAllAudioFiles()  {
        musicList = new ArrayList<Music>();
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        try {
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    //歌曲名
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String musicName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    //歌手名
                    String author = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    //歌曲大小 /K
                    Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//                    Bitmap artPic = setArtwork(getContext(),url);
                    if(size>1024*800){
                        Music music = new Music();
                        music.setName(musicName);
                        music.setAuthor(author);
                        music.setUrl(url);
                        music.setDuration(duration);
                        musicList.add(music);

                    }
                    cursor.moveToNext();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        if (musicList == null) {
            Toast.makeText(getContext(), "歌曲数据空空如也", Toast.LENGTH_SHORT).show();
            return null;
        }
        return musicList;
    }

    /**
     * 实现获取歌曲的专辑封面方法
     * @param context
     * @param url
     * @return
     */
    public static Bitmap setArtwork(Context context, String url) {
        Uri selectedAudio = Uri.parse(url);
        MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
        myRetriever.setDataSource(context, selectedAudio); // the URI of audio file
        byte[] artwork;

        artwork = myRetriever.getEmbeddedPicture();

        if (artwork != null) {
            Bitmap bMap = BitmapFactory.decodeByteArray(artwork, 0, artwork.length);
            //ivPic.setImageBitmap(bMap);

            return bMap;
        } else {
            //ivPic.setImageResource(R.drawable.ic_menu_camera);
            return BitmapFactory.decodeResource(context.getResources(), R.mipmap.music);
        }
    }
    /**
     * 格式化时间，将毫秒转换为分:秒格式，
     * 如果没有切换的话，那么歌曲的时间无法获取
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        // TODO Auto-generated method stub
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }

        return min + ":" + sec.trim().substring(0, 2);

    }

}
