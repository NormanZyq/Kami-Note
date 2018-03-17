package com.example.zyq.kaminotetest;

/**
 * Created by zyq on 2018/3/16.
 * 紧急程度和标签
 */

public class Extras {
    public Color color;
    public Level level;
    public Label[] labels;

    public Extras(Color color, Level level) {
        this.color = color;
        this.level = level;
    }
}

enum Color {
    RED, GREEN;
    //两项待定
}

/**
 * FOCUS            紧急重要    —— RED
 * FIT_IN           紧急不重要   ——
 * GOALS            重要不紧急   ——
 * BACK_BURNER      不紧急不重要 —— GREEN
 */
enum Level {
    FOCUS, FIT_IN, GOALS, BACK_BURNER;
}

class Label {
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