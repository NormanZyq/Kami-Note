package com.example.zyq.kaminotetest;

import org.litepal.crud.DataSupport;

public class Label extends DataSupport {
    private String labelName;

    public Label(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
