package com.example.zyq.kaminotetest.Class;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by zyq on 2018/6/6.
 */

public class Emotion extends DataSupport{

    private Date date = new Date();     //获得数据的时间

    private double positive = 0.0;      //积极指数

    private double negative = 0.0;      //消极指数

    /**
     * 构造器
     * @param date          通过腾讯云情感分析API获得这个数据的时间
     * @param positive      积极指数
     * @param negative      消极指数
     */
    public Emotion(Date date, double positive, double negative) {
        this.date = date;
        this.positive = positive;
        this.negative = negative;
    }

    /**
     * 无参构造器
     */
    public Emotion() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPositive() {
        return positive;
    }

    public void setPositive(double positive) {
        this.positive = positive;
    }

    public double getNegative() {
        return negative;
    }

    public void setNegative(double negative) {
        this.negative = negative;
    }
}
