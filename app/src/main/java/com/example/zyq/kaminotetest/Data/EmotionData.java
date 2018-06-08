package com.example.zyq.kaminotetest.Data;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyq on 2018/6/8.
 */

public class EmotionData extends DataSupport {
    private List<Double> emotionPositivePerWeek = new ArrayList<>();  //每周的积极指数

    private List<Double> emotionNegativePerWeek = new ArrayList<>();  //每周的消极指数


    public EmotionData() {
    }

    public EmotionData(List<Double> emotionPositivePerWeek, List<Double> emotionNegativePerWeek) {
        this.emotionPositivePerWeek = emotionPositivePerWeek;
        this.emotionNegativePerWeek = emotionNegativePerWeek;
    }

    public List<Double> getEmotionPositivePerWeek() {
        return emotionPositivePerWeek;
    }

    public void setEmotionPositivePerWeek(List<Double> emotionPositivePerWeek) {
        this.emotionPositivePerWeek = emotionPositivePerWeek;
    }

    public List<Double> getEmotionNegativePerWeek() {
        return emotionNegativePerWeek;
    }

    public void setEmotionNegativePerWeek(List<Double> emotionNegativePerWeek) {
        this.emotionNegativePerWeek = emotionNegativePerWeek;
    }
}
