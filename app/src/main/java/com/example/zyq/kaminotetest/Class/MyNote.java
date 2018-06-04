package com.example.zyq.kaminotetest.Class;

import org.litepal.crud.DataSupport;

/**
 * Created by zyq on 2018/2/3.
 * note类
 * Updated on 2018/3/17
 * 添加了extras成员，包含紧急程度和标签
 */

public class MyNote extends DataSupport {
    private String title;   //笔记标题

    private String content; //笔记内容

    private final String identifier;  //作为note的标识

    private String createdDate; //创建的日期

    private String lastEdited;  //最后编辑的日期

    private Extras extras;

//    private List<Label> labels = new ArrayList<>();

    private String[] labels = new String[]{};

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
        for (String labelName : labels) {
            if (labelName.equals(labelNameToSearch)) {
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

//    public List<Label> getLabels() {
//        return labels;
//    }
//
//    public void setLabels(List<Label> labels) {
//        this.labels = labels;
//    }


    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }
}
