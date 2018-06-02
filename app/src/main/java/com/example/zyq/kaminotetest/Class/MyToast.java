package com.example.zyq.kaminotetest.Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyq.kaminotetest.R;

/**
 * Created by zyq on 2018/2/24.
 * 来源：CSDN，作者信息已忘，向原作者致歉
 */

public class MyToast {
    private Toast mToast;
    public MyToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.eplay_toast, null);
        TextView textView = v.findViewById(R.id.textView1);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }
    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
