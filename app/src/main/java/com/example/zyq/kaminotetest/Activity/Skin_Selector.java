package com.example.zyq.kaminotetest.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.zyq.kaminotetest.R;

import java.util.ArrayList;
import java.util.List;

public class Skin_Selector extends AppCompatActivity implements View.OnClickListener{

    private  ColorSelector colorSelector_1;
    private  ColorSelector colorSelector_2;
    private  ColorSelector colorSelector_3;
    private  ColorSelector colorSelector_4;
    private  ColorSelector colorSelector_5;

    public List<ColorSelector> colors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_selector);
        //设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化布局元素
        initLayout();
    }

    public void initLayout(){
        colors = new ArrayList<>();
        //设置皮肤布局
        colorSelector_1 = new ColorSelector();
        colorSelector_2 = new ColorSelector();
        colorSelector_3 = new ColorSelector();
        colorSelector_4 = new ColorSelector();
        colorSelector_5 = new ColorSelector();

        colorSelector_1.linearLayout= findViewById(R.id.skin_1);
        colorSelector_1.checkBox = findViewById(R.id.checkbox_1);
        colorSelector_2.linearLayout = findViewById(R.id.skin_2);
        colorSelector_2.checkBox = findViewById(R.id.checkbox_2);
        colorSelector_3.linearLayout = findViewById(R.id.skin_3);
        colorSelector_3.checkBox = findViewById(R.id.checkbox_3);
        colorSelector_4.linearLayout = findViewById(R.id.skin_4);
        colorSelector_4.checkBox = findViewById(R.id.checkbox_4);
        colorSelector_5.linearLayout = findViewById(R.id.skin_5);
        colorSelector_5.checkBox = findViewById(R.id.checkbox_5);
        //设置按钮集合
        colors.add(colorSelector_1);
        colors.add(colorSelector_2);
        colors.add(colorSelector_3);
        colors.add(colorSelector_4);
        colors.add(colorSelector_5);
        //设置监听
        colorSelector_1.linearLayout.setOnClickListener(this);
        colorSelector_2.linearLayout.setOnClickListener(this);
        colorSelector_3.linearLayout.setOnClickListener(this);
        colorSelector_4.linearLayout.setOnClickListener(this);
        colorSelector_5.linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skin_1:
                setchecked(R.id.skin_1);
                break;
            case R.id.skin_2:
                setchecked(R.id.skin_2);
                break;
            case R.id.skin_3:
                setchecked(R.id.skin_3);
                break;
            case R.id.skin_4:
                setchecked(R.id.skin_4);
                break;
            case R.id.skin_5:
                setchecked(R.id.skin_5);
                break;
        }
    }

    /**
     * 用于设置选中checkbox
     * @param id 对应LinearLayout布局id
     */
    public void setchecked(int id){
        for(int i = 0;i < colors.size();i++){
            if(id == colors.get(i).linearLayout.getId()){
                colors.get(i).checkBox.setChecked(true);
            }
            else colors.get(i).checkBox.setChecked(false);
        }
    }
}

class ColorSelector{
    public  LinearLayout linearLayout;
    public  CheckBox checkBox;
}