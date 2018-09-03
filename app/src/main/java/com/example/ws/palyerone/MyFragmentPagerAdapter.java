package com.example.ws.palyerone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ws on 2018/4/17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
      private List<Fragment> list_fragment;
      private List<String> list_titles;
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> list_fragment,List<String> list_titles){
        super(fm);
        this.list_fragment = list_fragment;
        this.list_titles = list_titles;
    }
    @Override
    public Fragment getItem(int position){
        return list_fragment.get(position);
    }
    @Override
    public  int getCount(){
        return list_fragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_titles.get(position);
    }

    //
//    public CharSequence getPageTiles(int position){
//        return list_titles.get(position);
//    }
}
