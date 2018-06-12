package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zyq.kaminotetest.Activity.MainActivity;
import com.example.zyq.kaminotetest.Class.ColorForCircle;
import com.example.zyq.kaminotetest.Class.MyDate;
import com.example.zyq.kaminotetest.Data.DataClass;
import com.example.zyq.kaminotetest.R;
import com.example.zyq.kaminotetest.Utils.NoteUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PieChart mPieChart;
    private String[] Title = {"POSITIVE","NEGATIVE"};


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
        Description description = new Description();
        description.setText("心情指数");
         mPieChart.setDescription(description);
        // 设置偏移量
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        // 设置滑动减速摩擦系数
        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        mPieChart.setCenterText("现在还没有添加过日记呢");
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


        // 设置旋转角度   ？？
        mPieChart.setRotationAngle(1);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(false);

        // add a selection listener
        // mPieChart.setOnChartValueSelectedListener(this);
        // 判断今日是否初次启动程序
        String launchDate = new MyDate().getDate();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("launchLog", Context.MODE_PRIVATE);
        String lastLaunchDate = sharedPreferences.getString("launch_Date", "");

        ArrayList<PieEntry> data = new ArrayList<>();
        if(DataClass.mNote.size() != 0){
            if(!launchDate.equals(lastLaunchDate)){
                if(!MainActivity.isEdit){
                    //若文本未编辑，则显示平均数据并清空笔记位置缓存
                    data.add(new PieEntry((float)NoteUtils.INSTANCE.predictEmotion(),"Positive"));
                    data.add(new PieEntry((float)(1-NoteUtils.INSTANCE.predictEmotion()),"Negative"));
                    SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("notePosition",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    editor1.clear();
                    editor1.apply();
                }
                else if(MainActivity.isEdit){
                    //若笔记被编辑，则显示最新数据，保留笔记位置缓存
                    data.add( new PieEntry((float)DataClass.mNote.get(MainActivity.notePosition).getPositive(),"Positive"));
                    data.add( new PieEntry((float)DataClass.mNote.get(MainActivity.notePosition).getNegative(),"Negative"));
                }

                // 写入今天的日期，表示今天不再是首次登陆
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // 记录最后启动的日期
                editor.putString("launch_Date", new MyDate().getDate());
                editor.apply();
                mPieChart.setDrawCenterText(false);
            }
            else{//当日第二次打开程序时执行的操作
                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("notePosition",Context.MODE_PRIVATE);
                MainActivity.notePosition = sharedPreferences1.getInt("position",-1);
                if(MainActivity.notePosition == -1){
                    //若第一次使用未编辑数据，则显示平均数据
                    data.add( new PieEntry((float)NoteUtils.INSTANCE.predictEmotion(),"Positive"));
                    data.add(new PieEntry((float)(1-NoteUtils.INSTANCE.predictEmotion()),"Negative"));
                }else if(MainActivity.notePosition >= 0){
                    //若第一次编辑了数据，则显示最新数据
                    data.add( new PieEntry((float)DataClass.mNote.get(MainActivity.notePosition).getPositive(),"Positive"));
                    data.add( new PieEntry((float)DataClass.mNote.get(MainActivity.notePosition).getNegative(),"Negative"));
                /*data.put("data1", (float)DataClass.mNote.get(MainActivity.notePosition).getPositive());
                data.put("data2", (float)DataClass.mNote.get(MainActivity.notePosition).getNegative());*/
                }
                else{
                    System.out.println("error:static133");
                }
                mPieChart.setDrawCenterText(false);
/*        data.put("data3", 0.1f);
        data.put("data4", 0.1f);*/
            }
        }
        else{
            mPieChart.setDrawCenterText(true);
        }

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

    public void setData(ArrayList<PieEntry> data) {
        /*ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();*/
        //设置标题

/*        int i = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            // entry的输出结果如key0=value0等
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            float value = (float) entry.getValue();
            xVals.add(key);
            yVals1.add(new PieEntry(value, i++));
        }*/

        PieDataSet dataSet = new PieDataSet(data, "");
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        //饼图Item被选中时变化的距离
        dataSet.setSelectionShift(10f);
        //设置饼图文本

        // 添加颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
/*        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);*/
/*        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);*/
/*        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);*/
/*        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);*/
/*        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/
            for(int c: ColorForCircle.POSIADNEGA)
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
