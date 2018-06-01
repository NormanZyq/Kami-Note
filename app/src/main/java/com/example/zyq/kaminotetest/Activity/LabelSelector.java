package com.example.zyq.kaminotetest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.zyq.kaminotetest.Adapter.LabelSelectorAdapter;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class LabelSelector extends AppCompatActivity {

    private List<Label> mLabel = new ArrayList<>();
    private ListView labelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_selector);

        labelList = findViewById(R.id.label_list_in_selector);  //获得用以显示的ListView

        Intent intent = getIntent();    //获得intent
        int notePosition = intent.getIntExtra("note_position", 0);

        mLabel = DataSupport.findAll(Label.class);      //从数据库中获得所有标签

        LabelSelectorAdapter adapter = new LabelSelectorAdapter(this, R.layout.label_item_in_selector, mLabel);
        labelList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        labelList.setAdapter(adapter);

    }
}
