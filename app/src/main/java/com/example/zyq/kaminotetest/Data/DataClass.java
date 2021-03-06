package com.example.zyq.kaminotetest.Data;

import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.Class.MyNote;

import java.util.List;

/**
 * Created by zyq on 2018/6/3.
 * 用于存放各种数据例如mNote
 */

public class DataClass {
    public static List<MyNote> mNote;       //笔记列表

    public static List<Label> mLabel;       //标签列表

    public static int notePosition = 0;     //笔记位置

    public static long longClickPosition = 0;       //长按note的位置

    public static EmotionData emotionData = new EmotionData();

    public static boolean allowInternet = true;

}
