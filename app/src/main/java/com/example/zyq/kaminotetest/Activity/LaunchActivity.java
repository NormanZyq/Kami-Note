package com.example.zyq.kaminotetest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.zyq.kaminotetest.Class.MyDate;
import com.example.zyq.kaminotetest.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置启动图片
        setContentView(R.layout.activity_launch);

//        ImageView imageView = findViewById(R.id.img_launch_screen);
//        imageView.setImageResource();

        MyDate myDate = new MyDate();
        TextView date = findViewById(R.id.launch_date1);
        date.setText(myDate.getDay() + "");

        TextView date2 = findViewById(R.id.launch_date2);
        date2.setText(myDate.getYearAndMonth());

        Integer time = 1500;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                LaunchActivity.this.finish();
            }
        }, time);

    }
}
