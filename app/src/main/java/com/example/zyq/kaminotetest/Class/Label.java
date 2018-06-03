package com.example.zyq.kaminotetest.Class;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import fragment.HomeFragment;

public class Label extends DataSupport {
    private String labelName;

    private List<MyNote> notes = new ArrayList<>();

    private int count = 0;

    public List<MyNote> getNotes() {
        List<MyNote> gNote = new ArrayList<>();
        for (MyNote note : HomeFragment.mNote) {
            if (note.hasLabel(labelName)) {
                gNote.add(note);
                count++;
            }
        }
        return gNote;
    }

    public int calculateCount() {
        int count = 0;
        for (MyNote note : HomeFragment.mNote) {
            if (note.hasLabel(labelName)) {
                count++;
            }
        }
        this.count = count;
        return count;
    }

    /**
     * 添加传入的所有note到对应的label
     * @param notesToAdd    待添加的note列表
     */
    public void AddNotes(List<MyNote> notesToAdd) {
        notes.addAll(notesToAdd);
    }

    public void AddNote(MyNote noteToAdd) {
        notes.add(noteToAdd);
    }

    public void RemoveNotes(List<MyNote> notes) {
//        notes.
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
