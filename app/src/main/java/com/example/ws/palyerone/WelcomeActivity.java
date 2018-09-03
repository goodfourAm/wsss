package com.example.ws.palyerone;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class WelcomeActivity extends AppCompatActivity {

    private static final int UPDATE_SEEKBAR = 1;
    private Message message;
    private TextView tv_cancel_order_time;
    private long seconds = 3;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_SEEKBAR:
                    if (seconds > 0){
                        seconds--;
                        String s = Long.toString(seconds);
                        tv_cancel_order_time.setText(s);

                    }else {
                        //倒计时结束，do something<span style="white-space:pre;">         </span>
                    }
                    handler.sendEmptyMessageDelayed(1,1000);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ImageView imageView = (ImageView) findViewById(R.id.welcome_view);
        tv_cancel_order_time = (TextView) findViewById(R.id.tv_cancel_order_time);
        String s = Long.toString(seconds);
        tv_cancel_order_time.setText(s);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.1f);
        animator.setDuration(3000);//时间1s
        animator.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                message = new Message();
                message.what = UPDATE_SEEKBAR;
                handler.sendMessage(message);
            }
        }).start();

//        Handler handler = new Handler();
        //当计时结束,跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 3000);
    }
}
