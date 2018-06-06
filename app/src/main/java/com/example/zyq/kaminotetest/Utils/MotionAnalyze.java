package com.example.zyq.kaminotetest.Utils;

import com.example.zyq.kaminotetest.main.java.com.qcloud.Module.Wenzhi;
import com.example.zyq.kaminotetest.main.java.com.qcloud.Utilities.Json.JSONObject;

import java.util.TreeMap;

public class MotionAnalyze {

    public static void getMotionStatistic(String content){
        TreeMap<String,Object> config = new TreeMap<>();
        config.put("SecretId","AKIDe8NM5YehO4YroZDMN9fvynQq21mKkniR");
        config.put("SecretKey","d4HH3h6E8m9H9l7C2dy2qOgrpa8TbVCf");
        config.put("RequestMethod","POST");
        config.put("RegionDefault","sz");

        QcloudApiModuleCenter module = new QcloudApiModuleCenter(new Wenzhi(),config);

        TreeMap<String,Object> params = new TreeMap<>();
        params.put("content",content);
        params.put("type",2);

        String result = null;
        try{
            result = module.call("TextSentiment", params);
            JSONObject json_result = new JSONObject(result);
            Double positive = json_result.optDouble("positive");
            System.out.println(json_result+">>>>>>>>>>>>>>>>>>>>>");
            System.out.println(positive+">>>>>>>>>>>>>>>>>>>");
        }catch (Exception ex){
            System.out.println("error" + ex.getMessage());
        }
    }
}
