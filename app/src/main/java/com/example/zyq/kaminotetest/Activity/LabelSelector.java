package com.example.zyq.kaminotetest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.zyq.kaminotetest.Adapter.LabelSelectorAdapter;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.R;
import com.example.zyq.kaminotetest.Utils.NoteUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import fragment.HomeFragment;

public class LabelSelector extends AppCompatActivity {

    private List<Label> mLabel = new ArrayList<>();
    private ListView labelList;
    private MyNote sourceNote;
    public static boolean[] checked;
    int notePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_selector);

        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        labelList = findViewById(R.id.label_list_in_selector);  //获得用以显示的ListView

        Intent intent = getIntent();    //获得intent
        notePosition = intent.getIntExtra("note_position", 0);  //传入note的位置
        sourceNote = HomeFragment.mNote.get(notePosition);      //获取来源笔记

        mLabel = DataSupport.findAll(Label.class);      //从数据库中获得所有标签

        checked = new boolean[mLabel.size()];       //以note数量为大小初始化checked数组

        //自动标记这条笔记拥有的标签到checked数组，true表示拥有，false表示不拥有
        for (int i = 0, length = checked.length; i < length; i++) {
            if (sourceNote.hasLabel(mLabel.get(i).getLabelName())) {
                checked[i] = true;
            }
        }


        //显示标签的列表
        LabelSelectorAdapter adapter = new LabelSelectorAdapter(this, R.layout.label_item_in_selector, mLabel);
        labelList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        labelList.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selector_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            List<Label> labels = new ArrayList<>();
            for (int i = 0, length = checked.length; i < length; i++) {
                if (checked[i]) {
                    labels.add(HomeFragment.mLabel.get(i));
                }
            }
            System.out.println(labels.size()+">>>>>>>>>>>>>>");
            NoteUtils.INSTANCE.setLabels(sourceNote, labels);
            finish();
        }
        return true;
    }
}
