package fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zyq.kaminotetest.Activity.Skin_Selector;
import com.example.zyq.kaminotetest.Data.DataClass;
import com.example.zyq.kaminotetest.R;

/**
 * Created by TALK_SWORD on 2018/3/23.
 */

public class SettingsFragment extends Fragment {
    private String mfrom;
    private Button button;
    private Switch aSwitch;
    private TextView textView;
    public static int Color_id = 0;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_settings,null);
        button = view.findViewById(R.id.button);
        aSwitch = view.findViewById(R.id.switch1);
        textView = view.findViewById(R.id.text);

        //设置Toolbar
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        if(DataClass.allowInternet){
            aSwitch.setChecked(true);
            textView.setText("开启");
        }else{
            aSwitch.setChecked(false);
            textView.setText("关闭");
        }
        return view;
    }

    public static SettingsFragment newInstance(String from){
        SettingsFragment fragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mfrom = getArguments().getString("from");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Skin_Selector.class);
                startActivity(intent);
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("是否开启情感分析功能？");
                    builder.setMessage("如果允许，我们可能需要获得网络权限并取得您的数据进行分析，但是不会保存，是否允许？");
                    builder.setCancelable(false);
                    builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            textView.setText("开启");
                            DataClass.allowInternet = true;
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("allowances", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("allowInternet",true);
                            editor.apply();
                        }
                    });
                    builder.setNegativeButton("不允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            aSwitch.setChecked(false);
                            return;
                        }
                    });
                    builder.show();
//                    textView.setText("开启");
//                    DataClass.allowInternet = true;
//                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("allowances", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("allowInternet",true);
//                    editor.apply();
                }
                else{
                    textView.setText("关闭");
                    DataClass.allowInternet = false;
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("allowances", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("allowInternet",false);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Color_id",Context.MODE_PRIVATE);
        Color_id = sharedPreferences.getInt("id",0);
        if(Color_id != 0) {
            toolbar.setBackgroundResource(Color_id);
        }
    }
}
