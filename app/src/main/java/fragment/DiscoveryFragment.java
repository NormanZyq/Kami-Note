package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.zyq.kaminotetest.Adapter.MyPagerAdapter;
import com.example.zyq.kaminotetest.R;
import com.example.zyq.kaminotetest.Utils.MotionAnalyze;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TALK_SWORD on 2018/3/23.
 */

public class DiscoveryFragment extends Fragment {
    private String mfrom;
    private int Color_id;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_discovery,null);

        mTabLayout = view.findViewById(R.id.discovery_tab_layout);      //获取tabLayout
        mViewPager = view.findViewById(R.id.discovery_view_pager);      //获取viewPager
//        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);       //设置tabLayout可以滑动切换
        //设置Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Color_id", Context.MODE_PRIVATE);
        Color_id = sharedPreferences.getInt("id",0);
        if(Color_id != 0) {
            toolbar.setBackgroundResource(Color_id);
        }

        initViewPager();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中tab
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    public static DiscoveryFragment newInstance(String from){
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from",from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //测试文智api调用是否成功
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MotionAnalyze.getMotionStatistic("开心");
            }
        };
        new Thread(runnable).start();
        if(getArguments() != null){
            mfrom = getArguments().getString("from");
        }
    }

    //初始化viewPager
    private void initViewPager() {
        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        // 装填
        fragments.add(new EmotionStatusFragment());
        fragments.add(new EmotionStatusFragment());
        fragments.add(new EmotionStatusFragment());

//        fragments.add(new TwoFragment());
//        fragments.add(new ThreeFragment());
//        fragments.add(new FourFragment());
        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        // 给ViewPager设置适配器
        mViewPager.setAdapter(myPagerAdapter);

        // 使用 TabLayout 和 ViewPager 相关联
        mTabLayout.setupWithViewPager(mViewPager);


//        mTabLayout.addTab(mTabLayout.newTab().setText("心情波动"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("原始数据"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("小贴士"));
//
        mTabLayout.getTabAt(0).setText("心情波动");
        mTabLayout.getTabAt(1).setText("原始数据");
        mTabLayout.getTabAt(2).setText("小贴士");
    }

}
