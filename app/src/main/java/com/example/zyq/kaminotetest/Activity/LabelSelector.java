package com.example.zyq.kaminotetest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.zyq.kaminotetest.Adapter.LabelSelectorAdapter;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.R;
import com.example.zyq.kaminotetest.Utils.NoteUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import fragment.HomeFragment;

public class LabelSelector extends AppCompatActivity {

    private List<Label> mLabel = new ArrayList<>();
    private ListView labelList;
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
        notePosition = intent.getIntExtra("note_position", 0);

        mLabel = DataSupport.findAll(Label.class);      //从数据库中获得所有标签

        checked = new boolean[mLabel.size()];

        //显示标签的列表
        LabelSelectorAdapter adapter = new LabelSelectorAdapter(this, R.layout.label_item_in_selector, mLabel);
        labelList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        labelList.setAdapter(adapter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        List<Label> labels = new ArrayList<>();
        for (int i = 0, length = checked.length; i < length; i++) {
            if (checked[i]) {
                labels.add(HomeFragment.mLabel.get(i));
                System.out.println("添加一个");
            }
        }
        System.out.println(labels.size()+">>>>>>>>>>>>>>");
        NoteUtils.INSTANCE.setLabels(HomeFragment.mNote.get(notePosition), labels);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.selector_toolbar_menu, menu);
        return true;
    }
}
