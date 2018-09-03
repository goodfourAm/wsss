package com.example.ws.palyerone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ws.palyerone.R;
import com.example.ws.palyerone.adapter.RecyclerViewAdapter;
import com.example.ws.palyerone.Util;

/**
 * Created by ws on 2018/4/17.
 */

public class FragmentTwo extends Fragment {

//    private ArrayList<Music> musicList = null;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    public FragmentTwo() {

    }
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttwo, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

//        Toast.makeText(getContext(), "到了第二个界面", Toast.LENGTH_SHORT).show();

        if (Util.scanAllAudioFiles() != null) {
            adapter = new RecyclerViewAdapter(Util.scanAllAudioFiles());
        }
        recyclerView.setAdapter(adapter);
        return view;


    }



}
