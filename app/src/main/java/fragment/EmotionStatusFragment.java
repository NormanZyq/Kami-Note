package fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zyq.kaminotetest.Data.DataClass;
import com.example.zyq.kaminotetest.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmotionStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmotionStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmotionStatusFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LineChart mLineChart;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EmotionStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmotionStatusFragment.
     */
    public static EmotionStatusFragment newInstance(String param1, String param2) {
        EmotionStatusFragment fragment = new EmotionStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获得view
        View view = inflater.inflate(R.layout.fragment_emotion_status,null);

        // 获得图表
        mLineChart = view.findViewById(R.id.emotion_status);

//        if (true) {
        if (DataClass.emotionData.getEmotionPositivePerWeek().size() > 0) {
            //如果有事情感数据才设置并显示

            // 显示边界
            mLineChart.setDrawBorders(true);
            mLineChart.setTouchEnabled(false);

            // 设置数据，数据来源是DC中的积极指数
            List<Entry> entries = new ArrayList<>();

            // 显示最近一周的数据
            for (int i = 0; i < 7; i++) {
                float pos = 0;
                System.out.println("emotionstatus line 89 " + DataClass.emotionData.getEmotionPositivePerWeek().size());
                try {
                    pos = DataClass.emotionData.getEmotionPositivePerWeek().get(i).floatValue();
                    System.out.println("emotionstatus line 91 >>>>>>>" + pos);
                } catch (IndexOutOfBoundsException ex) {
                    if (DataClass.emotionData.getEmotionPositivePerWeek().size() > i) {
                        ex.printStackTrace();
                    }
//                    break;
                }
                entries.add(new Entry(i + 1, pos));
            }

//            //测试用：生成7个数据进行显示
//            for (int i = 0; i < 7; i++) {
////                float pos = (float) Math.random() * System.currentTimeMillis() % 1;
//                float pos = (float) (Math.random() % 0.5 * 2);
//                entries.add(new Entry(i + 1, pos));
//            }

            //一个LineDataSet就是一条线
            LineDataSet lineDataSet = new LineDataSet(entries, "积极指数");
            LineData data = new LineData(lineDataSet);
            mLineChart.setData(data);
        } else {
            //如果情感数据为空，则显示提示语句
            mLineChart.setVisibility(View.GONE);
            TextView hint = view.findViewById(R.id.emotion_status_hint);
            hint.setVisibility(View.VISIBLE);
        }



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
