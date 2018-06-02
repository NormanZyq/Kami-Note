package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zyq.kaminotetest.R;

/**
 * Created by TALK_SWORD on 2018/3/23.
 */

public class CalendarFragment extends Fragment {
    private String mfrom;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_calendar,null);
        return view;
    }

    public static CalendarFragment newInstance(String from){
        CalendarFragment fragment = new CalendarFragment();
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
}
