package com.example.ws.palyerone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ws.palyerone.Music;
import com.example.ws.palyerone.R;

import java.util.List;

/**
 * Created by ws on 2018/4/27.
 */

public class ListViewAdapter extends ArrayAdapter {


    //ListView

    private int resourceId;
    public ListViewAdapter(Context context, int textViewResourceId, List<Music> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music myMusic = (Music) getItem(position);
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else {
            view = convertView;
        }
        TextView nameText = (TextView) view.findViewById(R.id.musicName);
        TextView authorText = (TextView) view.findViewById(R.id.author);
        nameText.setText(myMusic.getName());
        authorText.setText(myMusic.getAuthor());

        return view;



    }
}

