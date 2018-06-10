package fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_settings,null);
        button = view.findViewById(R.id.button);
        aSwitch = view.findViewById(R.id.switch1);
        textView = view.findViewById(R.id.text);
        if(DataClass.allowInternet){
            aSwitch.setChecked(true);
        }else{
            aSwitch.setChecked(false);
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
                    textView.setText("开启");
                    DataClass.allowInternet = true;
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("allowances", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("allowInternet",true);
                    editor.apply();
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
}
