package com.example.zyq.kaminotetest.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zyq.kaminotetest.Class.MyDate;
import com.example.zyq.kaminotetest.Class.MyToast;
import com.example.zyq.kaminotetest.Data.DataClass;
import com.example.zyq.kaminotetest.R;
import com.example.zyq.kaminotetest.Utils.MotionAnalyze;
import com.example.zyq.kaminotetest.Utils.NoteUtils;
import com.githang.statusbar.StatusBarCompat;

import java.util.UUID;

//import android.widget.TextView;

/**
 * Created by zyq on 2018/3/6.
 * 笔记创建页面
 */

public class CreateNote extends AppCompatActivity {
    private String title;
    private String content;
    private String identifier;
    private int Color_id;
    public MyDate createdDate;

    EditText noteTitle;
    EditText noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("Color_id", Context.MODE_PRIVATE);
        Color_id = sharedPreferences.getInt("id",0);
        if(Color_id != 0){
            toolbar.setBackgroundResource(Color_id);
            StatusBarCompat.setStatusBarColor(this,getResources().getColor(Color_id), true);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        noteTitle = findViewById(R.id.title_editor);
        noteContent = findViewById(R.id.content_editor);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Confirmation();
        }
        return super.onKeyDown(keyCode, event);
    }

    //应用toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cre_menu, menu);
        return true;
    }

    //点击toolbar的内容时启用的操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finish:
                return save();
            case android.R.id.home:
                Confirmation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean save() {
        createdDate = new MyDate();
        title = noteTitle.getText().toString().trim();
        content = noteContent.getText().toString().trim();
        //去掉空格如果内容为空则进行提示
        if (title.equals("") && content.equals("")) {
            finish();
            return false;
        } else {
            identifier = UUID.randomUUID().toString();  //设置UUID
            NoteUtils.INSTANCE.saveNote(title, content, identifier, createdDate);  //保存到数据库
            MyToast.makeText(CreateNote.this, "保存成功", Toast.LENGTH_SHORT).show();
//            MainActivity.notePosition = DataSupport.count(MyNote.class) - 1;
            //测试文智api调用是否成功
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    MotionAnalyze.getMotionStatistic(DataClass.mNote.get(DataClass.mNote.size()-1));
                }
            };
            new Thread(runnable).start();
            finish();
            return true;
        }
    }

    public void Confirmation() {
        if ( !noteTitle.getText().toString().trim().equals("") ||
                !noteContent.getText().toString().equals("") ) {
            //有输入时就发起提醒
            AlertDialog.Builder backConfirmation = MainActivity.buildAlertDialog(CreateNote.this,
                    "是否保存？", null);
            backConfirmation.setPositiveButton("保存并返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    noteTitle = findViewById(R.id.title_editor);
//                    noteContent = findViewById(R.id.content_editor);
                    createdDate = new MyDate();
                    identifier = UUID.randomUUID().toString();  //设置UUID
                    title = noteTitle.getText().toString();     //设置标题
                    content = noteContent.getText().toString(); //设置内容
                    NoteUtils.INSTANCE.saveNote(title, content, identifier, createdDate);
                    //测试文智api调用是否成功
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            MotionAnalyze.getMotionStatistic(DataClass.mNote.get(DataClass.mNote.size()-1));
                        }
                    };
                    new Thread(runnable).start();
                    finish();
                }
            });
            backConfirmation.setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            backConfirmation.show();
        } else {
            finish();
        }
    }
}
