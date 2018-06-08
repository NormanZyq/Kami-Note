package fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zyq.kaminotetest.Data.DataClass;
import com.example.zyq.kaminotetest.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class StatisticFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PieChart mPieChart;


    public StatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 显示百分比
        mPieChart.setUsePercentValues(true);
        // 描述信息
        // mPieChart.setDescription();
        // 设置偏移量
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        // 设置滑动减速摩擦系数
        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        mPieChart.setCenterText("测试饼图，中间文字");
        /*
            设置饼图中心是否是空心的
            true 中间是空心的，环形图
            false 中间是实心的 饼图
         */
        mPieChart.setDrawHoleEnabled(false);
        /*
            设置中间空心圆孔的颜色是否透明
            true 透明的
            false 非透明的
         */
        //mPieChart.setHoleColorTransparent(true);
        // 设置环形图和中间空心圆之间的圆环的颜色
        mPieChart.setTransparentCircleColor(Color.WHITE);
        // 设置环形图和中间空心圆之间的圆环的透明度
        mPieChart.setTransparentCircleAlpha(110);

        // 设置圆孔半径
        mPieChart.setHoleRadius(58f);
        // 设置空心圆的半径
        mPieChart.setTransparentCircleRadius(61f);
        // 设置是否显示中间的文字
        mPieChart.setDrawCenterText(true);


        // 设置旋转角度   ？？
        mPieChart.setRotationAngle(1);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(false);

        // add a selection listener
        // mPieChart.setOnChartValueSelectedListener(this);

        TreeMap<String, Float> data = new TreeMap<>();
        data.put("data1", (float) DataClass.mNote.get(DataClass.mNote.size() - 1).getPositive());
        data.put("data2", (float)DataClass.mNote.get(DataClass.mNote.size()-1).getNegative());
        data.put("data3", 0.1f);
        data.put("data4", 0.1f);
        setData(data);

        // 设置动画
        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        // 设置显示的比例
        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    public void setData(TreeMap<String, Float> data) {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

        int i = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            // entry的输出结果如key0=value0等
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            float value = (float) entry.getValue();
            xVals.add(key);
            yVals1.add(new PieEntry(value, i++));
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // 添加颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        // dataSet.setSelectionShift(0f);

        PieData data1 = new PieData(dataSet);
        data1.setValueFormatter(new PercentFormatter());
        data1.setValueTextSize(10f);
        data1.setValueTextColor(Color.BLACK);
        mPieChart.setData(data1);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        mPieChart = view.findViewById(R.id.pie_chart);
        return view;
    }

}
