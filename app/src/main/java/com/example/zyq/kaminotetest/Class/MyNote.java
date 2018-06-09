package com.example.zyq.kaminotetest.Class;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyq on 2018/2/3.
 * note类
 */

public class MyNote extends DataSupport {
    private String title;   //笔记标题

    private String content; //笔记内容

    @Column(unique = true)
    private final String identifier;  //作为note的标识

    private String createdDate; //创建的日期

    private String lastEdited;  //最后编辑的日期

    private Extras extras;

//    private list
    private List<Label> labels = new ArrayList<>();

    private boolean isPositive = false;

    private double positive = 0;

    private double negative = 0;

    //构造方法，传入标题、内容、标识、创建日期。（最后编辑的日期另外设置）
    public MyNote(String title, String content, String identifier, String createdDate, Extras extras) {
        this.title = title;
        this.content = content;
        this.identifier = identifier;
        this.createdDate = createdDate;
        this.extras = extras;
    }

    public MyNote(String title, String content, String identifier, String createdDate) {
        this.title = title;
        this.content = content;
        this.identifier = identifier;
        this.createdDate = createdDate;
    }

    public boolean hasLabel(String labelNameToSearch) {
//        System.out.println(labels.size());
//        String labelNamesString[] = (String[]) labels;
//        System.out.println("mynote line 59, " + this.title + "  labelSize = " + labels.size());
        for (Label label : labels) {
            if (label.getLabelName().equals(labelNameToSearch)) {
                return true;
            }
        }
        return false;
    }

    /*------get set 方法------------*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getCreateDate() {
        return createdDate;
    }

    public String getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

    public void setNoteEmotion(Emotion noteEmotion) {
//        this.noteEmotion = noteEmotion;
        positive = noteEmotion.getPositive();
        negative = noteEmotion.getNegative();
    }

//    public double getPositive() {
//        return this.noteEmotion.getPositive();
//    }

    public double getPositive() {
        return positive;
    }

//    public double getNegative(){return this.noteEmotion.getNegative();}

    public double getNegative() {
        return negative;
    }
}
