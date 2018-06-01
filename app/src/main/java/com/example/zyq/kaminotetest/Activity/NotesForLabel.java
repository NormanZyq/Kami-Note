package com.example.zyq.kaminotetest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyq.kaminotetest.Adapter.NoteAdapter2;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.Class.MyToast;
import com.example.zyq.kaminotetest.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static fragment.HomeFragment.ACTIVITY_TAG;

public class NotesForLabel extends AppCompatActivity {

    private List<Label> labels = new ArrayList<>();
    private Label label = null;                     //定义标签
    private String labelname = null;               //定义传过来的标签名
    private List<MyNote> notes = null;
    private RecyclerView noteListView;
    private TextView tv_noMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_for_label);

        //设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        //获取到用户点击的label名
        Intent intent = getIntent();
        labelname = intent.getStringExtra("label_name");
        //获取到对应名称的label对象
        labels = DataSupport.where("labelName = ?",labelname).find(Label.class);
        label = labels.get(0);

        notes = label.getNotes();

        noteListView = findViewById(R.id.note_list);
        tv_noMore = findViewById(R.id.no_more);

        if(notes.size() != 0){
            refreshNoteListView(noteListView);
        }else{
            tv_noMore.setVisibility(View.VISIBLE);
        }
    }

    public void refreshNoteListView(RecyclerView recyclerView) {
        if (recyclerView != null&&this != null) {
//            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));  //添加分割线
            LinearLayoutManager layoutManager = new LinearLayoutManager(this); //???
            layoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
            layoutManager.setReverseLayout(true);//列表翻转
            recyclerView.setLayoutManager(layoutManager);
            NoteAdapter2 noteAdapter2 = new NoteAdapter2(notes, this);
            recyclerView.setAdapter(noteAdapter2);
            noteListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0,666,0,"删除");
                }
            });
            if (notes.size() != 0) {
                //如果存在数据且"没有更多内容"还在，则设置为GONE
                if (tv_noMore.getVisibility() == View.VISIBLE) {
                    tv_noMore.setVisibility(View.GONE);
                }
            } else {
                if (tv_noMore.getVisibility() != View.VISIBLE) {
                    tv_noMore.setVisibility(View.VISIBLE);
                }
            }

        } else {
            Log.d(ACTIVITY_TAG, "refreshNoteListView: 传入了空的recyclerView参数");
            MyToast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
}
