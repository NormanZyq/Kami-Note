package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Utilities.Json.JSONObject;
import com.example.zyq.kaminotetest.R;
import com.example.zyq.kaminotetest.Utils.QcloudApiModuleCenter;
import com.qcloud.Wenzhi;

import java.util.TreeMap;

/**
 * Created by TALK_SWORD on 2018/3/23.
 */

public class NoteFragment extends Fragment {
    private String mfrom;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_note,null);
        return view;
    }

    public static NoteFragment newInstance(String from){
        NoteFragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mfrom = getArguments().getString("from");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ApiCollector("黄沙百战穿金甲，不破楼兰终不还。");
            }
        };
        new Thread(runnable).start();
    }


    public void ApiCollector(String content){
        TreeMap<String,Object> config = new TreeMap<String,Object>();
        config.put("SecretId","AKIDe8NM5YehO4YroZDMN9fvynQq21mKkniR");
        config.put("SecretKey","d4HH3h6E8m9H9l7C2dy2qOgrpa8TbVCf");
        config.put("RequestMode","Post");
        config.put("DefaultRegion","gz");

        QcloudApiModuleCenter module = new QcloudApiModuleCenter(new Wenzhi(),config);

        TreeMap<String,Object> params = new TreeMap<String,Object>();
        params.put("content",content);
        params.put("type",2);

        String result = null;
        try{
            result = module.call("TextSentiment",params);
            JSONObject json_result = new JSONObject(result);
            System.out.println(">>>>>>>>>>>>>>>go");
            System.out.println(json_result);
        }catch(Exception ex){
            System.out.println(">>>>>>>>>>>>>>wrong");
            System.out.println(ex.getMessage());
        }

    }
}
