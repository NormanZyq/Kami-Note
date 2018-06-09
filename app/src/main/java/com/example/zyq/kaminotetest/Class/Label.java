package com.example.zyq.kaminotetest.Class;

import com.example.zyq.kaminotetest.Data.DataClass;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Label extends DataSupport {
    private String labelName = "";      //标签的名字

    private List<MyNote> notes = new ArrayList<>();     //这条标签拥有的笔记

    private int count = 0;      //拥有标签的数量，可能可以去掉了，因为此count在不出错的情况下应该等于notes.size()

    public List<MyNote> getNotes2() {
        List<MyNote> gNote = new ArrayList<>();
        for (MyNote note : DataClass.mNote) {
            if (note.hasLabel(labelName)) {
                gNote.add(note);
                count++;
            }
        }
//        notes = gNote;
        return gNote;
    }

    public boolean hasNote(MyNote sourceNote) {
        for (MyNote note : notes) {
            if (note == sourceNote) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断这个标签是否拥有某条笔记
     * @param uuid  待检测笔记的uuid
     * @return  如果在notes列表中找到了对应笔记就返回true，否则为false
     */
    public boolean hasNote(String uuid) {
        for (MyNote note : notes) {
            if (note.getIdentifier().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果存在对应的note，则从notes中删除
     * @param uuid  待检测的note的id
     * @return  成功检测到并且删除返回true，否则为false
     */
    public boolean removeNoteIfExist(String uuid) {
        for (int i = 0, size = notes.size(); i < size; i++) {
            if (notes.get(i).getIdentifier().equals(uuid)) {
                notes.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * 计算拥有笔记的数量
     * @return  返回count的大小（笔记的数量）
     */
    public int calculateCount() {
        int count = 0;
        for (MyNote note : DataClass.mNote) {
            if (note.hasLabel(labelName)) {
                count++;
            }
        }
        this.count = count;
        return count;
    }

    public List<MyNote> getNotes() {
        return notes;
    }

    public void setNotes(List<MyNote> notes) {
        this.notes = notes;
    }

    public Label(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getCount() {
        return count;
    }
}
