package com.example.zyq.kaminotetest;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by zyq on 2018/3/16.
 * 紧急程度和标签
 */

public class Extras {
    public Color color;
    public Level level;
    public List<Label> labels;

    public Extras(Color color, Level level, List<Label> labels) {
        this.color = color;
        this.level = level;
        this.labels = labels;
    }
}

enum Color {
    RED, BLUE, YELLOW, GREEN;
}

/**
 * FOCUS            紧急重要    —— RED
 * FIT_IN           紧急不重要   ——  YELLOW
 * GOALS            重要不紧急   ——  BLUE
 * BACK_BURNER      不紧急不重要 —— GREEN
 */
enum Level {
    FOCUS, FIT_IN, GOALS, BACK_BURNER;
}

