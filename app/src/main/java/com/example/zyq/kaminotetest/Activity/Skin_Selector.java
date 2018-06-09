package com.example.zyq.kaminotetest.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zyq.kaminotetest.R;
import com.example.zyq.kaminotetest.Utils.ActivityController;
import com.example.zyq.kaminotetest.Utils.DataGenerator;
import com.example.zyq.kaminotetest.Utils.ToolbarController;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import fragment.HomeFragment;
import fragment.SettingsFragment;

public class Skin_Selector extends AppCompatActivity implements View.OnClickListener{

    private  ColorSelector colorSelector_1;
    private  ColorSelector colorSelector_2;
    private  ColorSelector colorSelector_3;
    private  ColorSelector colorSelector_4;
    private  ColorSelector colorSelector_5;

/*    public int colorAlpha;*/

    public List<ColorSelector> colors;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private View headerlayout;
    private RelativeLayout relativeLayout_drawerhead;
    private int Color_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_selector);
        //设置Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("Color_id",Context.MODE_PRIVATE);
        Color_id = sharedPreferences.getInt("id",0);
        if(Color_id != 0) {
            toolbar.setBackgroundResource(Color_id);
            StatusBarCompat.setStatusBarColor(this,getResources().getColor(Color_id), true);
        }

        bottomNavigationView = ActivityController.activities.get(0).findViewById(R.id.navigation_view);
        navigationView = ActivityController.activities.get(0).findViewById(R.id.nav_view);
        headerlayout = navigationView.getHeaderView(0);
        relativeLayout_drawerhead = headerlayout.findViewById(R.id.drawer_head_relativelayout);
        //初始化布局元素
        initLayout();
    }

    //点击Toolbar后的操作


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done:
                //todo
                if(ColorSelector.checked_index != -1) {
                   bottomNavigationView.setItemBackgroundResource(colors.get(ColorSelector.checked_index).image_color_id);
                   relativeLayout_drawerhead.setBackgroundResource(colors.get(ColorSelector.checked_index).image_color_id);
                    StatusBarCompat.setStatusBarColor(ActivityController.activities.get(0),getResources().getColor(colors.get(ColorSelector.checked_index).image_color_id), true);
                    //System.out.println(ToolbarController.toolbars.size());
                    //传递颜色数值，方便Toolbar同步颜色
                    HomeFragment.Color_id = colors.get(ColorSelector.checked_index).image_color_id;
                    //保存颜色数值
                    SharedPreferences sharedPreferences = getSharedPreferences("Color_id", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id",colors.get(ColorSelector.checked_index).image_color_id);
                    editor.commit();
                    finish();
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
        return true;
    }

    //应用Toolbar并设置菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
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
        colorSelector_1.image_color_id = R.color.colorAccent;
        colorSelector_2.linearLayout = findViewById(R.id.skin_2);
        colorSelector_2.checkBox = findViewById(R.id.checkbox_2);
        colorSelector_2.image_color_id = R.color.colorPrimary;
        colorSelector_3.linearLayout = findViewById(R.id.skin_3);
        colorSelector_3.checkBox = findViewById(R.id.checkbox_3);
        colorSelector_3.image_color_id = R.color.cardview_dark_background;
        colorSelector_4.linearLayout = findViewById(R.id.skin_4);
        colorSelector_4.checkBox = findViewById(R.id.checkbox_4);
        colorSelector_4.image_color_id= R.color.holo_orange_light;
        colorSelector_5.linearLayout = findViewById(R.id.skin_5);
        colorSelector_5.checkBox = findViewById(R.id.checkbox_5);
        colorSelector_5.image_color_id = R.color.holo_orange_dark;
        //设置按钮集合
        colors.add(colorSelector_1);
        colors.add(colorSelector_2);
        colors.add(colorSelector_3);
        colors.add(colorSelector_4);
        colors.add(colorSelector_5);
        //设置监听
        colorSelector_1.linearLayout.setOnClickListener(this);
        colorSelector_1.checkBox.setOnClickListener(this);
        colorSelector_2.linearLayout.setOnClickListener(this);
        colorSelector_2.checkBox.setOnClickListener(this);

        colorSelector_3.linearLayout.setOnClickListener(this);
        colorSelector_3.checkBox.setOnClickListener(this);

        colorSelector_4.linearLayout.setOnClickListener(this);
        colorSelector_4.checkBox.setOnClickListener(this);

        colorSelector_5.linearLayout.setOnClickListener(this);
        colorSelector_5.checkBox.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.skin_1:
            case R.id.checkbox_1:
                setchecked(R.id.skin_1);
                break;
            case R.id.skin_2:
            case R.id.checkbox_2:
                setchecked(R.id.skin_2);
                break;
            case R.id.skin_3:
            case R.id.checkbox_3:
                setchecked(R.id.skin_3);
                break;
            case R.id.skin_4:
            case R.id.checkbox_4:
                setchecked(R.id.skin_4);
                break;
            case R.id.skin_5:
            case R.id.checkbox_5:
                setchecked(R.id.skin_5);
                break;
        }
    }

    /**
     * 用于设置选中checkbox
     * @param id 对应LinearLayout布局id
     */
    public void setchecked(int id){
        for (int i = 0; i < colors.size(); i++) {
            if (id == colors.get(i).linearLayout.getId()) {
                colors.get(i).checkBox.setChecked(true);
                ColorSelector.checked_index = i;
            } else {
                colors.get(i).checkBox.setChecked(false);
            }
        }
    }
}

class ColorSelector{
    public  LinearLayout linearLayout;
    public  CheckBox checkBox;
    public int image_color_id;
    public static int checked_index = -1;
}