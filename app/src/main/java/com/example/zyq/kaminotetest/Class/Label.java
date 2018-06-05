package com.example.zyq.kaminotetest.Class;

import com.example.zyq.kaminotetest.Data.DataClass;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Label extends DataSupport {
    private String labelName = "";

    private List<MyNote> notes = new ArrayList<>();

    private int count = 0;

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

//    /**
//     * 添加传入的所有note到对应的label
//     * @param notesToAdd    待添加的note列表
//     */
//    public void AddNotes(List<MyNote> notesToAdd) {
//        notes.addAll(notesToAdd);
//    }
//
//    public void AddNote(MyNote noteToAdd) {
//        notes.add(noteToAdd);
//    }


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
