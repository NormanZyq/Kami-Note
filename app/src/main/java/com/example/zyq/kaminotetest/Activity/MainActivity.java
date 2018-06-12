package com.example.zyq.kaminotetest.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyq.kaminotetest.Adapter.LabelAdapter;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.Class.MyDate;
import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.Class.MyToast;
import com.example.zyq.kaminotetest.Data.DataClass;
import com.example.zyq.kaminotetest.Data.EmotionData;
import com.example.zyq.kaminotetest.R;
import com.example.zyq.kaminotetest.Utils.ActivityController;
import com.example.zyq.kaminotetest.Utils.DataGenerator;
import com.example.zyq.kaminotetest.Utils.NoteUtils;
import com.githang.statusbar.StatusBarCompat;

import org.litepal.crud.DataSupport;

import fragment.HomeFragment;

/**
 * Created by zyq on 2018/3/6.
 * 项目名称：Kami Note
 * MainActivity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String ACTIVITY_TAG = "MainActivity";  //打印日志的TAG
    public BottomNavigationView bottomNavigationView;            //底部栏引用
    private Fragment[] fragments;                                  //布局管理列表
    public static int Color_id;
    public RelativeLayout relativeLayout_drawerhead;
    public NavigationView navigationView;
    public View headerlayout;
    public static boolean isEdit = false;

    public static MainActivity mainActivity;
    private DrawerLayout mDrawerLayout;         //滑动菜单
    private long mExitTime = 0;                 //记录点击返回按钮的时间
    private TextView textAddLabel;
    private ImageView imageAddLabel;

    public static int notePosition = -1;             //记录笔记位置
    public ListView labelListView;
    public static int longClickPosition = 0;    //
    private HomeFragment fragment;
//    public RecyclerView labelListView2;

    //当App启动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //将活动加入管理列表
        ActivityController.addActivity(this);

        fragments = DataGenerator.getfragments("KamiNote");  //初始化列表
        initview();
        mDrawerLayout = findViewById(R.id.drawer_layout);   //滑动菜单

        labelListView = findViewById(R.id.label_list2);

        //设置toolbar的左侧菜单为显示状态
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        DataClass.mLabel = DataSupport.findAll(Label.class);    //获得标签
        DataClass.mNote = DataSupport.findAll(MyNote.class, true);    //获得笔记
        try {
            DataClass.emotionData = DataSupport.findAll(EmotionData.class, true).get(0);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("mainac line 99 gggggggg");
        }

        textAddLabel = findViewById(R.id.text_add_label);
        imageAddLabel = findViewById(R.id.image_add_label);

        refreshLabelListView(labelListView);

        imageAddLabel.setOnClickListener(this);
        textAddLabel.setOnClickListener(this);

        // 判断今日是否初次启动程序
        String launchDate = new MyDate().getDate();
        SharedPreferences sharedPreferences = getSharedPreferences("launchLog",Context.MODE_PRIVATE);
        String lastLaunchDate = sharedPreferences.getString("launchDate", "");



//        System.out.println("mainact line 114 " + DataClass.mNote.get(4).getPositive());

//        System.out.println("mainac line 108" + DataClass.mNote.get(0).getPositive());

        if (sharedPreferences.getBoolean("firstLaunch", true)) {
            AlertDialog.Builder builder = buildAlertDialog(this, "是否开启情感分析功能？",
                    "如果允许，我们可能需要获得网络权限并取得您的数据进行分析，但是不会保存，是否允许？");
            builder.setCancelable(false);
            builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataClass.allowInternet = true;
                    //写入数据，表示允许上传数据
                    SharedPreferences sharedPreferences = getSharedPreferences("allowances", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("allowInternet", true);
                    editor.apply();
                }
            });
            builder.setNegativeButton("不允许", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataClass.allowInternet = false;
                    //写入数据，表示不允许上传数据
                    SharedPreferences sharedPreferences = getSharedPreferences("allowances", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("allowInternet", false);
                    editor.apply();
                }
            });
            builder.show();

            //写入true，表示程序不是第一次启动
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstLaunch", false);
            editor.apply();
        } else {
            //不是第一次启动时获取是否允许伤处数据的boolean
            DataClass.allowInternet = getSharedPreferences("allowances",
                    Context.MODE_PRIVATE).getBoolean("allowInternet", true);
        }



        // 如果今天首次启动
        if (!launchDate.equals(lastLaunchDate)) {

            // 去掉七天以前的用于显示图表的情感数据
            while (DataClass.emotionData.getEmotionPositivePerWeek().size() >= 7) {
                DataClass.emotionData.getEmotionPositivePerWeek().remove(0);
                DataClass.emotionData.getEmotionNegativePerWeek().remove(0);
            }

//            //从数据库清空emotionData
//            DataSupport.deleteAll(EmotionData.class);

            // 计算昨天以前的心情波动
            NoteUtils.INSTANCE.calculateEmotion();
            System.out.println("mainac line 127 >>>>>>> boolean = " + DataClass.emotionData.save());
//            DataClass.emotionData.save();
            // 写入今天的日期，表示今天不再是首次登陆
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // 记录最后启动的日期
            editor.putString("launchDate", new MyDate().getDate());
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLabelListView(labelListView);
    }

    //初始化view
    private void initview(){
        bottomNavigationView = findViewById(R.id.navigation_view);
        navigationView = findViewById(R.id.nav_view);
        headerlayout = navigationView.getHeaderView(0);
        relativeLayout_drawerhead = headerlayout.findViewById(R.id.drawer_head_relativelayout);

        SharedPreferences sharedPreferences = getSharedPreferences("Color_id",Context.MODE_PRIVATE);
        //设置颜色
        Color_id = sharedPreferences.getInt("id",0);
        if(Color_id != 0){
           //设置系统状态栏颜色
            StatusBarCompat.setStatusBarColor(this,getResources().getColor(Color_id), true);
            bottomNavigationView.setItemBackgroundResource(Color_id);   //底部栏颜色设置
            relativeLayout_drawerhead.setBackgroundResource(Color_id);  //drawerlayout头部颜色设置
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onTabItemSelected(item.getItemId());
                return true;
            }
        });
        onTabItemSelected(R.id.tab_menu_home);
    }

    private void onTabItemSelected(int id){
        Fragment fragment = null;
        switch (id) {
            case R.id.tab_menu_home:
                fragment = fragments[0];
                break;
            case R.id.tab_menu_discovery:
                fragment = fragments[1];
                break;
            case R.id.tab_menu_attention:
                fragment = fragments[2];
                break;
//            case R.id.tab_menu_profile:
//                fragment = fragments[3];
//                break;
        }
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }

    /**
     * 刷新侧滑菜单的笔记列表
     * ！！！一定要优化！！！
     *
     * @param listView
     */
    public void refreshLabelListView(ListView listView) {
        if (listView != null) {
            LabelAdapter adapter = new LabelAdapter(this, R.layout.label_item, DataClass.mLabel);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            listView.setAdapter(adapter);
        }
    }

    //点击返回按钮的操作（"再按一次退出程序"）
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                MyToast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                finish();
            }
            return true;
//            作者：Carson_Ho
//            链接：https://www.jianshu.com/p/3dab35223b79
//            來源：简书
//            著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
        }
        return super.onKeyDown(keyCode, event);
    }

    public static AlertDialog.Builder buildAlertDialog(Context context, String alertTitle,
                                                String alertMessage) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(alertTitle);
        dialog.setMessage(alertMessage);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("请输入标签");    //设置对话框标题

        final EditText addLabel = new EditText(MainActivity.this);
        addLabel.setSingleLine(true);

        builder.setView(addLabel);
        builder.setCancelable(true);
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String labelName = addLabel.getText().toString();

                try {
                    DataSupport.where("labelName = ?", labelName).find(Label.class).get(0);
                    MyToast.makeText(MainActivity.this, "该标签已存在", Toast.LENGTH_SHORT).show();
                    return;

                } catch (Exception ex) {
                    System.out.println("标签正常");
                }

                if (labelName.length() > 10) {
                    MyToast.makeText(MainActivity.this, "标签过长", Toast.LENGTH_SHORT).show();
                } else {
                    Label label = new Label(labelName);
//                    HomeFragment.mLabel.add(label);
                    label.save();
                    DataClass.mLabel.add(label);

                    MyToast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
                //TODO
//                        MainActivity.mLabel = mLabel;
                refreshLabelListView(labelListView);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }


    /*//当点击长按菜单时要做的东西
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 666:   //点击了长按后出现的删除键时
                final MyNote myNoteTemp = mNote.get(longClickPosition);
                mNote.get(longClickPosition).delete();
                mNote.remove(longClickPosition);
                refreshNoteListView(noteListView);
                Snackbar.make(mainView, "删除成功", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mNote.add(myNoteTemp);
                                myNoteTemp.save();
                                refreshNoteListView(noteListView);
                            }
                        }).show();
                break;
        }
        return super.onContextItemSelected(item);
    }*/

}

