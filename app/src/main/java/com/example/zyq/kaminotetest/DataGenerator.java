package com.example.zyq.kaminotetest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zyq.kaminotetest.R;
import fragment.CalendarFragment;
import fragment.HomeFragment;
import fragment.NoteFragment;
import fragment.ProfileFragment;

public class DataGenerator {
    public static final int []mTabRes = new int[]{R.drawable.tab_home_selector,R.drawable.tab_discovery_selector,R.drawable.tab_attention_selector,R.drawable.tab_profile_selector};
    public static final int []mTabResPressed = new int[]{R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_category_selected,R.drawable.ic_tab_strip_icon_pgc_selected,R.drawable.ic_tab_strip_icon_profile_selected};
    public static final String []mTabTitle = new String[]{"首页","发现","关注","我的"};

    public static Fragment[] getfragments(String from){
        Fragment fragment[] = new Fragment[4];
        fragment[0] = HomeFragment.newInstance(from);
        fragment[1] = NoteFragment.newInstance(from);
        fragment[2] = CalendarFragment.newInstance(from);
        fragment[3] = ProfileFragment.newInstance(from);
        return fragment;
    }

    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
