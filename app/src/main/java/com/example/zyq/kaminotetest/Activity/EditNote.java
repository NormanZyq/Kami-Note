package com.example.zyq.kaminotetest.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zyq.kaminotetest.Class.MyDate;
import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.Class.MyToast;
import com.example.zyq.kaminotetest.Utils.NoteUtils;
import com.example.zyq.kaminotetest.R;

import fragment.HomeFragment;

/**
 * 编辑界面
 * 点击note的时候跳转到此Activity
 * 复用代码较多，有待优化
 * Updated on 2018/3/15 21:17
 */
public class EditNote extends AppCompatActivity {
    private String title;
    private String content;
    EditText noteTitle;
    EditText noteContent;
    public MyNote myNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //设置和应用Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        //从NoteAdapter中获得传输的intent并获取note的position
        Intent intent = getIntent();
        int notePosition = intent.getIntExtra("note_position", -1);

        if (notePosition != -1) {
            /*
            直接从MainActivity中获得位置而没有传值
            可以优化，使用intent携带
             */
            myNote = HomeFragment.mNote.get(notePosition);

            noteTitle = findViewById(R.id.title_editor);    //笔记标题输入框
            noteContent = findViewById(R.id.content_editor);    //笔记内容输入框

            title = myNote.getTitle();  //笔记标题
            content = myNote.getContent();  //笔记内容

            //设置标题，如果标题是默认生成的"无标题"，则在编辑时自动去掉
            if (title.equals("无标题")) {
                noteTitle.setText("");
            } else {
                noteTitle.setText(title);
            }
            //设置内容
            noteContent.setText(myNote.getContent());

        } else {
            //如果position传输错误则进行提示
            Toast.makeText(EditNote.this, "获取笔记位置失败", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backConfirmation();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cre_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MyDate date = new MyDate();
        switch (item.getItemId()) {
            case R.id.finish:
                title = noteTitle.getText().toString();
                content = noteContent.getText().toString();

                if (title.trim().equals("") && content.trim().equals("")) {
                    MyToast.makeText(EditNote.this, "标题和内容不能为空",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
                //如果没有修改，则直接finish
                if ((title.equals(myNote.getTitle()) ||
                        (title.equals("") && myNote.getTitle().equals("无标题"))) &&
                        content.equals(myNote.getContent())) {
                    finish();
                    return true;
                }
                if (title.trim().equals("")) {
                    title = "无标题";
                }
                if (content.trim().equals("")) {
                    content = "";
                }
                myNote.setLastEdited(date.toString());
                NoteUtils.INSTANCE.updateNote(myNote, title, content, date);
                MyToast.makeText(EditNote.this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
                break;

            case android.R.id.home:
                //返回确认
                backConfirmation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void backConfirmation() {
        //从NoteAdapter中获得传输的intent并获取note的position
        Intent intent = getIntent();
        int notePosition = intent.getIntExtra("note_position", -1);
        myNote = HomeFragment.mNote.get(notePosition);

        noteTitle = findViewById(R.id.title_editor);    //笔记标题
        noteContent = findViewById(R.id.content_editor);    //笔记内容

        title = myNote.getTitle();
        content = myNote.getContent();

        if (title.equals(noteTitle.getText().toString().equals("") ? "无标题" : noteTitle.getText().toString()) &&
                content.equals(noteContent.getText().toString())) {
            finish();
        } else {
            AlertDialog.Builder backConfirmation = new AlertDialog.Builder(this);
            backConfirmation.setTitle("是否保存？");
            backConfirmation.setCancelable(true);
            backConfirmation.setPositiveButton("保存并返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final MyDate date = new MyDate();

                    title = noteTitle.getText().toString();
                    content = noteContent.getText().toString();

                    if (title.trim().equals("") && content.trim().equals("")) {
                        MyToast.makeText(EditNote.this, "标题和内容不能为空",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //如果没有修改，则直接finish
                    if (title.equals(myNote.getTitle()) && content.equals(myNote.getContent())) {
                        finish();
                    }
                    if (title.trim().equals("")) {
                        title = "无标题";
                    }
                    if (content.trim().equals("")){
                        content = "";
                    }
                    NoteUtils.INSTANCE.updateNote(myNote, title, content, date);
                    MyToast.makeText(EditNote.this, "保存成功", Toast.LENGTH_SHORT).show();
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
        }
    }

}
