package com.example.ws.palyerone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

import com.bumptech.glide.Glide;
import com.example.ws.palyerone.fragment.FragmentOne;
import com.example.ws.palyerone.fragment.FragmentThree;
import com.example.ws.palyerone.fragment.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

import services.MusicPlayerService;
import services.WebMusicPlayerService;

import static java.lang.Thread.sleep;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final int UPDATE_TEXT = 1;
    private TextView one_name;
    private TextView one_author;
    private ImageView bottom_image;
    private ImageButton play_pause;
    private ImageButton bottom_next;
    private LinearLayout linearLayout;
    private Music music;
    private MyReceiver receiver;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> list;
    private List<String> list_titles;
    private int position;
    private int change_num = 1;
    private int playOne;
    private int dif = -1;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_pause:
                if(dif == -1) {
                    if(position != -1){
                        final int num_one = 2;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getBaseContext(), MusicPlayerService.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("num",num_one);
                                bundle.putInt("position",position);
                                bundle.putInt("dif",dif);
                                intent.putExtras(bundle);
                                getBaseContext().startService(intent);
                            }
                        }).start();
                        if(change_num == 1){
                            play_pause.setImageResource(R.drawable.ic_play_white_36dp);
                            change_num = 2;
                        }else if(change_num == 2){
                            play_pause.setImageResource(R.drawable.ic_pause_white_36dp);
                            change_num = 1;
                        }
                    }
                } else if (dif == 1) {
                    final int num_one = 2;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent webIntent = new Intent(getBaseContext(), MusicPlayerService.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("num",num_one);
                            bundle.putInt("position",position);
                            bundle.putInt("dif",dif);
                            webIntent.putExtras(bundle);
                            getBaseContext().startService(webIntent);
                        }
                    }).start();
                    if(change_num == 1){
                        play_pause.setImageResource(R.drawable.ic_play_white_36dp);
                        change_num = 2;
                    }else if(change_num == 2){
                        play_pause.setImageResource(R.drawable.ic_pause_white_36dp);
                        change_num = 1;
                    }

                }
                break;
            case R.id.bottom_next:
                if (dif == -1) {
                    position ++;
                    if (position >= Util.scanAllAudioFiles().size()) {
                        position = 0;
                    }
                    final int num_two = 1;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), MusicPlayerService.class);
                            Bundle bundle = new Bundle();
//                        bundle.putSerializable("songInfo",musicNext);
                            bundle.putInt("num",num_two);
                            bundle.putInt("position",position);
                            bundle.putInt("dif",dif);
                            intent.putExtras(bundle);
                            getBaseContext().startService(intent);
                        }
                    }).start();
                } else if (dif == 1) {
                    final int num_two = 3;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), MusicPlayerService.class);
                            Bundle bundle = new Bundle();
//                        bundle.putSerializable("songInfo",musicNext);
                            bundle.putInt("num",num_two);
                            bundle.putInt("position",position);
                            bundle.putInt("dif",dif);
                            intent.putExtras(bundle);
                            getBaseContext().startService(intent);
                        }
                    }).start();
                }
                break;
            case R.id.activity_base:
                if (dif == -1) {
                    if (position != -1){
                        DisplayActivity.runActivity(getBaseContext(),Util.scanAllAudioFiles().get(position),position,playOne,dif);
                    }else {
                        Toast.makeText(getBaseContext(),"没有歌曲信息",Toast.LENGTH_SHORT).show();
                    }
                } else if (dif == 1) {
                    Toast.makeText(getBaseContext(),"no",Toast.LENGTH_SHORT).show();
                }


        }
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            final int play = bundle.getInt("play");
            dif = bundle.getInt("dif");

            playOne = play;

            if (dif == 1) {
                position = bundle.getInt("count");
                final WebMusic webMusic = FragmentOne.getWebMusic().get(position);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        one_name.setText(webMusic.getMusicName());
                        one_author.setText(webMusic.getMusicSinger());

                        Glide.with(MyApplication.getContext())
                                .load(webMusic.getMusicImage())
                                .into(bottom_image);
                        if(play == 1){
                            play_pause.setImageResource(R.drawable.ic_pause_white_36dp);
                        }else if(play == 2){
                            play_pause.setImageResource(R.drawable.ic_play_white_36dp);
                        }
                    }
                });
            }else if (dif == -1){
                position = bundle.getInt("count");
                music = Util.scanAllAudioFiles().get(position);
//            play_pause.setImageResource(R.drawable.ic_play_white_36dp);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        one_name.setText(music.getName());
                        one_author.setText(music.getAuthor());
                        bottom_image.setImageBitmap(Util.setArtwork(getBaseContext(),music.getUrl()));
                        if(play == 1){
                            play_pause.setImageResource(R.drawable.ic_pause_white_36dp);
                        }else if(play == 2){
                            play_pause.setImageResource(R.drawable.ic_play_white_36dp);
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) mFloatView.findViewById(R.id.activity_base);
        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.ljq.activity.CountService");
        MainActivity.this.registerReceiver(receiver,filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        //实例化
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        one_name = (TextView) mFloatView.findViewById(R.id.one_name);
        one_author = (TextView) mFloatView.findViewById(R.id.one_author);
        play_pause = (ImageButton) mFloatView.findViewById(R.id.play_pause);
        bottom_next = (ImageButton) mFloatView.findViewById(R.id.bottom_next);
        bottom_image = (ImageView) mFloatView.findViewById(R.id.bottom_image);
        play_pause.setOnClickListener(this);
        bottom_next.setOnClickListener(this);
        linearLayout.setOnClickListener(this);


        list = new ArrayList<>();
        list_titles = new ArrayList<>();
        Fragment();


//        获取SharedPreferences存储的上一次关闭前的 position
        SharedPreferences pref = getSharedPreferences("dataPosition",MODE_PRIVATE);
        position = pref.getInt("poSi",-1);
//        if (position > Util.scanAllAudioFiles().size()) {
//            position = 0;
//
//        }
//        if(position != -1){
//            final Music mu = Util.scanAllAudioFiles().get(position);
//            one_name.setText(mu.getName());
//            one_author.setText(mu.getAuthor());
//            bottom_image.setImageBitmap(Util.setArtwork(getBaseContext(),mu.getUrl()));
//        }
        if(position != -1){
            try {
                final Music mu = Util.scanAllAudioFiles().get(position);
                one_name.setText(mu.getName());
                one_author.setText(mu.getAuthor());
                bottom_image.setImageBitmap(Util.setArtwork(getBaseContext(),mu.getUrl()));
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            try {
                final Music mu = Util.scanAllAudioFiles().get(0);
                one_name.setText(mu.getName());
                one_author.setText(mu.getAuthor());
                bottom_image.setImageBitmap(Util.setArtwork(getBaseContext(),mu.getUrl()));

            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences("dataPosition",MODE_PRIVATE).edit();
        editor.putInt("poSi",position);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
//        unbindService(mServiceConnection);
        super.onDestroy();
//        SharedPreferences.Editor editor = getSharedPreferences("dataPosition",MODE_PRIVATE).edit();
//        editor.putInt("poSi",position);
//        editor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //设置标题为空
    @Override
    protected void onStart() {
        super.onStart();
        setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Toast.makeText(MainActivity.this, "add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove:
                Toast.makeText(MainActivity.this, "remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }

    public void Fragment(){
        list_titles.add("音乐");
        list_titles.add("列表");
        list_titles.add("百度");
        //Tablayout标题
        tabLayout.addTab(tabLayout.newTab().setText(list_titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list_titles.get(2)));
        //添加页面
        list.add(new FragmentOne());
        list.add(new FragmentTwo());
        list.add(new FragmentThree());
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(this.getSupportFragmentManager(),list, list_titles);
        viewPager.setAdapter(myFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}




