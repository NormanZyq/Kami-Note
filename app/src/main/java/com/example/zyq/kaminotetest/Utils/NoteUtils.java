package com.example.zyq.kaminotetest.Utils;

import com.example.zyq.kaminotetest.Class.Extras;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.Class.MyDate;
import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.Data.DataClass;

import java.util.List;

/**
 * Created by zyq on 2018/3/2.
 * 使用枚举制作的单例note工具类
 * 日后将考虑修改，目前使用范围过于局限
 */

public enum NoteUtils {
    INSTANCE;   //单个实例

    /*-----------------方法-----------------*/

    /**
     * 保存note的方法
     *
     * @param title       笔记的标题
     * @param content     笔记的详细内容
     * @param identifier  笔记的标识，UUID
     * @param createdDate 笔记的创建日期
     *                    编辑日期另外添加
     */
    public void saveNote(String title, String content, String identifier, MyDate createdDate, Extras extras) {
        MyNote note = new MyNote(title, content, identifier, createdDate.toString(), extras);
        note.setLastEdited(createdDate.toString());
        DataClass.mNote.add(note);
        note.save();
    }

    public void saveNote(String title, String content, String identifier, MyDate createdDate) {
        MyNote note = new MyNote(title, content, identifier, createdDate.toString());
        note.setLastEdited(createdDate.toString());
//        note.setLabels(new String[]{});
        DataClass.mNote.add(note);
        note.save();
    }

    //更新note的方法
    public void updateNote(MyNote note, String title, String content, MyDate editedDate) {
        note.setTitle(title);
        note.setContent(content);
        note.setLastEdited(editedDate.toString());
        note.save();
    }

    //移除note的方法
    public void removeNote(List<MyNote> mNote, int position) {
        mNote.remove(position);
    }

    /**
     * 为笔记添加标签
     *
     * @param note   待添加标签的笔记
     * @param labels 待添加的标签列表
     */
    public void setLabels(MyNote note, List<Label> labels) {
//        note.setLabels(labels);
        note.save();
    }

    public void setLabels(MyNote note, String[] labels) {
        note.setLabels(labels);
        note.save();
    }
}
