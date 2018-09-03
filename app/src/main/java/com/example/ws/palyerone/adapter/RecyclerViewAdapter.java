package com.example.ws.palyerone.adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ws.palyerone.Music;
import com.example.ws.palyerone.MyApplication;
import com.example.ws.palyerone.R;
import com.example.ws.palyerone.Util;

import java.util.List;

import services.MusicPlayerService;
import services.WebMusicPlayerService;

/**
 * Created by ws on 2018/4/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Music> musicList;
    private Bitmap bitmap;
    private int position;
    private int selectedPosition = -5;// 选中的位置
    static class ViewHolder extends  RecyclerView.ViewHolder{
        View musicView;
        TextView music_Name;
        TextView author;
        ImageView imageView;
        public ViewHolder(View view){

            super(view);
            musicView = view;
            imageView = (ImageView) view.findViewById(R.id.image);
            music_Name = (TextView) view.findViewById(R.id.musicName);
            author = (TextView) view.findViewById(R.id.author);

        }

    }
    public RecyclerViewAdapter(List<Music> muList){
        this.musicList = muList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
            holder.musicView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    position = holder.getAdapterPosition();

                    if (selectedPosition != position) {
                        notifyItemChanged(selectedPosition);
                        holder.imageView.setImageResource(R.drawable.select_play);
                        selectedPosition = position;
                    }
                    final int num = 1;
                    final int dif = -1;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MyApplication.getContext(), MusicPlayerService.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("num",num);
                            bundle.putInt("position",position);
                            bundle.putInt("dif",dif);
                            intent.putExtras(bundle);
                            MyApplication.getContext().startService(intent);
                        }
                    }).start();
                holder.musicView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        holder.imageView.setImageResource(R.drawable.actionbar_music_prs);
                    }
                });
                }
            });

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Music music = musicList.get(position);
        holder.music_Name.setText(music.getName());
        holder.author.setText(music.getAuthor());
        bitmap = Util.setArtwork(MyApplication.getContext(), music.getUrl());
        if (bitmap != null) {
            holder.imageView.setImageBitmap(bitmap);
        }

//        if (selectedPosition == position) {
//            holder.selectImageView.setVisibility(View.VISIBLE);
//        } else {
//            holder.selectImageView.setVisibility(View.INVISIBLE);
//        }

    }
    @Override
    public int getItemCount() {
        return musicList.size();
    }
}
