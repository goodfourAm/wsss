package com.example.ws.palyerone.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.ws.palyerone.MyApplication;
import com.example.ws.palyerone.R;
import com.example.ws.palyerone.Util;
import com.example.ws.palyerone.WebMusic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import services.MusicPlayerService;
import services.WebMusicPlayerService;

public class WebMusicAdapter extends RecyclerView.Adapter<WebMusicAdapter.ViewHolder>{

    private List<WebMusic> list = new ArrayList<>();
    private int selectedPosition = -1;

    public WebMusicAdapter(List<WebMusic> lists) {
        this.list = lists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View musicView;
        TextView music_Name;
        TextView author;
        ImageView imageView;
        View picView;
        public ViewHolder(View view) {
            super(view);
            picView = view;
            musicView = view;
            imageView = (ImageView) view.findViewById(R.id.image);
            music_Name = (TextView) view.findViewById(R.id.musicName);
            author = (TextView) view.findViewById(R.id.author);
        }
    }

    @NonNull
    @Override
    public WebMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.picView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                final int num = 1;
                final int dif = 1 ;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(MyApplication.getContext(), WebMusicPlayerService.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("num",num);
//                        bundle.putInt("dif",dif);
//                        bundle.putInt("position",position);
////                        bundle.putString("path",path);
////                        bundle.putInt("dif",dif);
//                        intent.putExtras(bundle);
//                        MyApplication.getContext().startService(intent);
//                    }
//                }).start();


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MyApplication.getContext(), MusicPlayerService.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("num",num);
                        bundle.putInt("dif",dif);
                        bundle.putInt("position",position);
                        intent.putExtras(bundle);
                        MyApplication.getContext().startService(intent);
                    }
                }).start();
            }
        });
        return holder;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WebMusic webMusic = list.get(position);
        holder.music_Name.setText(webMusic.getMusicName());
        holder.author.setText(webMusic.getMusicSinger());
//        Bitmap bitmap = Util.setArtwork(MyApplication.getContext(), webMusic.getMusicImage());
//        if (bitmap != null) {
//            holder.imageView.setImageBitmap(bitmap);
//        }
        Glide.with(MyApplication.getContext())
                .load(webMusic.getMusicImage())
                .into(holder.imageView);
    }


}
