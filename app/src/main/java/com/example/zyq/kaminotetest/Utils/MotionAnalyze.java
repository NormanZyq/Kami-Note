package com.example.zyq.kaminotetest.Utils;

import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.main.java.com.qcloud.Module.Wenzhi;
import com.example.zyq.kaminotetest.main.java.com.qcloud.Utilities.Json.JSONObject;

import java.util.TreeMap;

public class MotionAnalyze {

    /**
     * 调用api进行情感分析
     * @param note 用户编辑的笔记//可用于更新note中的情感属性
     */
    public static void getMotionStatistic(MyNote note){
        TreeMap<String,Object> config = new TreeMap<>();
        config.put("SecretId","AKIDe8NM5YehO4YroZDMN9fvynQq21mKkniR");
        config.put("SecretKey","d4HH3h6E8m9H9l7C2dy2qOgrpa8TbVCf");
        config.put("RequestMethod","POST");
        config.put("RegionDefault","sz");

        QcloudApiModuleCenter module = new QcloudApiModuleCenter(new Wenzhi(),config);

        TreeMap<String,Object> params = new TreeMap<>();
        params.put("content",note.getContent());
        params.put("type",2);

        String result = null;
        try{
            result = module.call("TextSentiment", params);
            JSONObject json_result = new JSONObject(result);
            Double positive = json_result.optDouble("positive");
            Double negative = json_result.optDouble("negative");
        }catch (Exception ex){
            System.out.println("error" + ex.getMessage());
        }
    }
}