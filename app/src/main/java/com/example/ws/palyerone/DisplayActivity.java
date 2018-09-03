package com.example.ws.palyerone;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import services.MusicPlayerService;

/**
 * Created by ws on 2018/4/23.
 */

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener,ServiceConnection{
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Music current_music;
    private TextView textView;
    private ImageButton play_pause;
    private ImageView song_image;
    private MyReceiver receiver;
//    private MyReceiverTwo receiverTwo;
    int num = -1;
    int posi = 0;
    int play = -1;
    private  SeekBar seekBar;
    private static final int UPDATE_SEEKBAR = 1;
    private Message message;
    private int dif;


    private MusicPlayerService.MusicController mMusicController = new MusicPlayerService.MusicController();



//Handler更新播放进度条
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_SEEKBAR:
                    try {
                        if(mMusicController != null){
                            seekBar.setMax((int)mMusicController.getMusicDuration()/1000);
                            seekBar.setProgress((int)mMusicController.getPosition()/1000);
                        }else {
                            Toast.makeText(getBaseContext(),"null",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessageDelayed(1,400);
                    break;
            }
        }
    };





    //    广播接收service传值更新UI
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            final int position = bundle.getInt("count");
            final int play = bundle.getInt("play");
//            final int currentposition = bundle.getInt("currentposition");
//            final int duration = bundle.getInt("duration");
            final Music music = Util.scanAllAudioFiles().get(position);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(music.getName());
//                    seekBar.setMax(duration);
//                    seekBar.setProgress(currentposition);
                    song_image.setImageBitmap(Util.setArtwork(getBaseContext(),music.getUrl()));
                    if(play == 1){
                        play_pause.setImageResource(R.drawable.ic_pause_white_36dp);
                    }else {
                        play_pause.setImageResource(R.drawable.ic_play_white_36dp);
                    }
                }
            });
        }
    }
//    public class MyReceiverTwo extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Bundle bundleBar = intent.getExtras();
//            final int duration = bundleBar.getInt("duration");
//            final int currentposition =  bundleBar.getInt("currentposition");
//            Toast.makeText(getBaseContext(),currentposition,Toast.LENGTH_SHORT).show();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    seekBar.setMax(duration/1000);
//                    seekBar.setProgress(currentposition/1000);
//                }
//            });
//        }
//    }

//设置back键返回上层界面而当前avtivity没有finish，下次直接调用该activity；
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_item);
//广播接收者注册
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.ljq.activity.CountService");
        DisplayActivity.this.registerReceiver(receiver,filter);
//        receiverTwo = new MyReceiverTwo();
//        IntentFilter filterBar = new IntentFilter();
//        filterBar.addAction("com.ljq.activity.updateSeekBar");
//        DisplayActivity.this.registerReceiver(receiverTwo,filterBar);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                message = new Message();
                message.what = UPDATE_SEEKBAR;
                handler.sendMessage(message);
            }
        }).start();

//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                try{
////                    long duration = mMusicController.getMusicDuration();
////                    long currentposition = mMusicController.getPosition();
//                    seekBar.setMax(100);
//                    seekBar.setProgress(30);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        timer.schedule(task,10,50);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        play_pause = (ImageButton) findViewById(R.id.btn_display);
//        Button button_pause = (Button) findViewById(R.id.btn_pause);
        ImageButton button_last = (ImageButton) findViewById(R.id.btn_last);
        ImageButton button_next = (ImageButton) findViewById(R.id.btn_next);
        song_image = (ImageView) findViewById(R.id.song_image);
//        Button button_back = (Button) findViewById(R.id.btn_back);
        textView = (TextView) findViewById(R.id.Music_name);
        dif = getIntent().getIntExtra("dif",0);
        if (getIntent().getSerializableExtra("currentMusic") == null) {
            Toast.makeText(getApplicationContext(), "对不起，没有获取到当前音乐对象", Toast.LENGTH_SHORT).show();
            return;
        } else {
            current_music = (Music) getIntent().getSerializableExtra("currentMusic");
            posi = getIntent().getIntExtra("position", 0);
            play = getIntent().getIntExtra("play",0);
            dif = getIntent().getIntExtra("dif",dif);
            textView.setText(current_music.getName());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    song_image.setImageBitmap(Util.setArtwork(getBaseContext(),current_music.getUrl()));
                }
            }).start();
            song_image.setImageBitmap(Util.setArtwork(getBaseContext(),current_music.getUrl()));
            if(play == 1){
                play_pause.setImageResource(R.drawable.ic_pause_white_36dp);
            }


            play_pause.setOnClickListener(this);
//            button_pause.setOnClickListener(this);
            button_last.setOnClickListener(this);
            button_next.setOnClickListener(this);
//
//            Toast.makeText(getApplicationContext(), "当前音乐名称为：" + current_music.getName(), Toast.LENGTH_SHORT).show();
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int currentProgress = seekBar.getProgress()*1000;
//                    Toast.makeText(getBaseContext(),Integer.toString(currentProgress),Toast.LENGTH_SHORT).show();
                    mMusicController.setPosition(currentProgress);
                }
            });
        }
        //button_back.setOnClickListener(this);
//        thread = new Thread(new SeekBarThread());
//        // 启动线程
//        thread.start();
//        seekBar.setMax((int)mMusicController.getMusicDuration());
    }

//    class SeekBarThread implements Runnable {
//
//        @Override
//        public void run() {
//            while (mMusicController != null) {
//                // 将SeekBar位置设置到当前播放位置
//                seekBar.setMax((int)mMusicController.getMusicDuration());
//                seekBar.setProgress((int) mMusicController.getPosition());
//                try {
//                    // 每100毫秒更新一次位置
//                    Thread.sleep(100);
//                    //播放进度
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("");
    }


    public static void runActivity(Context context, Music music, int position,int playOne, int dif) {

        Intent intent = new Intent(context, DisplayActivity.class);
        intent.putExtra("currentMusic",music);
        intent.putExtra("position", position);
        intent.putExtra("play", playOne);
        intent.putExtra("dif",dif);
        context.startActivity(intent);

    }
    public static void webRunActivity(Context context, WebMusic music, int position,int playOne) {

        Intent intent = new Intent(context, DisplayActivity.class);
        intent.putExtra("currentMusic",music);
        intent.putExtra("position", position);
        intent.putExtra("play", playOne);
//        intent.putExtra("dif",diff);
        context.startActivity(intent);

    }

//    public static void play() {
//        try {
//            mediaPlayer.reset();
//            String path = current_music.getUrl();
//            //mediaPlayer.reset();
//            mediaPlayer.reset();
//            mediaPlayer.setDataSource(path);
////            mediaPlayer.prepare();
//            mediaPlayer.prepareAsync();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (!mediaPlayer.isPlaying()) {
//            mediaPlayer.start();
//        }
//    }

//    private void updateSeekBar(){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mMusicController != null){
//                    long total = mMusicController.getMusicDuration();
//                    long mCurrentPosition = mMusicController.getPosition();
//                    seekBar.setProgress((int) mCurrentPosition);//seekbar同步歌曲进度
//                    seekBar.setMax((int)total);//seekbar设置总时长
//
//                }
//                mHandler.postDelayed(this,1000);//延迟一秒运行线程
//            }
//        });
//    }

    /**
     * 当服务与当前绑定对象，绑定成功，服务onBind方法调用并且返回之后
     * 回调给这个方法
     *
     * @param name
     * @param service IBinder 就是服务 onBind 返回的对象
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mMusicController = ((MusicPlayerService.MusicController) service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mMusicController = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_display:
//                if(!mediaPlayer.isPlaying()) {
//                    try {
//                        mediaPlayer.start();
//                    } catch (Exception d) {
//                        d.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(MyApplication.getContext(), "歌曲已经在播放", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                num = 2;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getBaseContext(), MusicPlayerService.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("songInfo",current_music);
                        bundle.putInt("num",num);
                        bundle.putInt("position",posi);
                        bundle.putInt("dif",dif);
                        intent.putExtras(bundle);
                        getBaseContext().startService(intent);
                        bindService(intent, DisplayActivity.this, BIND_AUTO_CREATE);
                    }
                }).start();

//                handler.sendEmptyMessage(UPDATE);  //发送Message
                break;
//            case R.id.btn_pause:
////                if (mediaPlayer.isPlaying() || mediaPlayer != null) {
////                    mediaPlayer.pause();
////                } else if (mediaPlayer != null){
////                    initMediaPlayer();
////                    mediaPlayer.start();
////                }
//                num = 2;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(getBaseContext(), MusicPlayerService.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("num",num);
//                        bundle.putInt("position",posi);
//                        intent.putExtras(bundle);
//                        getBaseContext().startService(intent);
//                    }
//                }).start();
//                break;
            case R.id.btn_next:
                posi++;
                if (posi >= Util.scanAllAudioFiles().size()) {
                    posi = 0;
                }
                 num = 1;
                final Music musicNext = Util.scanAllAudioFiles().get(posi);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getBaseContext(), MusicPlayerService.class);
                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("songInfo",musicNext);
                        bundle.putInt("num",num);
                        bundle.putInt("position",posi);
                        bundle.putInt("dif",dif);
                        intent.putExtras(bundle);
                        getBaseContext().startService(intent);
                    }
                }).start();
                textView.setText(musicNext.getName());
                break;
            case R.id.btn_last:
                posi--;
                if(posi < 0 ){
                    posi = Util.scanAllAudioFiles().size()-1;
                }
                final int numb = 1;
                final Music musicLast = Util.scanAllAudioFiles().get(posi);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getBaseContext(), MusicPlayerService.class);
                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("songInfo",musicLast);
                        bundle.putInt("num",numb);
                        bundle.putInt("position",posi);
                        bundle.putInt("dif",dif);
                        intent.putExtras(bundle);
                        getBaseContext().startService(intent);
                    }
                }).start();
                textView.setText(musicLast.getName());
            default:
                break;
        }
    }


    private void initMediaPlayer(){
        try {
            mediaPlayer.reset();
            String path = current_music.getUrl();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void intentMusic(){
        Intent in = new Intent(getBaseContext(),BaseActivity.class);
        in.putExtra("position",posi);
        getBaseContext().startActivity(in);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
//        try {
//            if(mediaPlayer != null || mediaPlayer.isPlaying()){
//                mediaPlayer.stop();
//                mediaPlayer.release();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        unbindService(this);

    }
//    @Override
//    public void onClick(View v) {
//        int num = -1;
//        Intent intent = new Intent(DisplayActivity.this,MusicPlayerService.class);
//        switch (v.getId()) {
//            case R.id.btn_display:
//                num = 1;
//                break;
//            case R.id.btn_pause:
//                num = 2;
//                break;
//            default:
//                break;
//        }
//        intent.putExtra("currentMusic",current_music);
//        intent.putExtra("num",num);
//        startActivity(intent);
//    }




}

