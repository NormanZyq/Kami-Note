package com.example.zyq.kaminotetest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyq on 2018/3/6.
 * 项目名称：Kami Note
 * MainActivity
 * Updated on 2018/3/17.
 */

public class MainActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "MainActivity";  //打印日志的TAG

    private DrawerLayout mDrawerLayout;         //滑动菜单
    private TextView tv_noMore;                 //没有更多内容的文本
    private long mExitTime = 0;                 //记录点击返回按钮的时间
    private LinearLayout mainView;
    private LabelAdapter labelListAdapter;

    public static List<MyNote> mNote;           //保存note的列表
    public static List<Label> mLabel;           //
    public static int notePosition;             //记录笔记位置
    public RecyclerView noteListView;           //RecyclerView 的note 列表
    public ListView labelListView;
    public static int longClickPosition = 0;    //
//    public RecyclerView labelListView2;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
////                    noteListView.setVisibility(View.INVISIBLE);
////                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
////                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
////                    noteListView.setVisibility(View.VISIBLE);
////                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    //当App启动
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = findViewById(R.id.main_linear_layout);

        //设置toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_noMore = findViewById(R.id.no_more); //没有更多内容
        mDrawerLayout = findViewById(R.id.drawer_layout);   //滑动菜单
        noteListView = findViewById(R.id.note_list);    //note列表
        labelListView = findViewById(R.id.label_list2);

        //设置toolbar的左侧菜单为显示状态
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        mLabel = DataSupport.findAll(Label.class);

        //从数据库中读取存在的笔记
//        mNoteTemp = DataSupport.findAll(MyNote.class);
        mNote = DataSupport.findAll(MyNote.class);
        mLabel = DataSupport.findAll(Label.class);
        //判断是否读取到了数据
        if (mNote.size() != 0) {
            refreshNoteListView(noteListView);  //刷新
        } else {
            //如果读取到的内容为空，就将mNote设置为新的List，以备保存
            mNote = new ArrayList<>();
            //将"没有更多内容"从布局显示
            tv_noMore.setVisibility(View.VISIBLE);
        }
        if (mLabel.size() != 0) {
//            refreshLabelListView(labelListView2);   //刷新
            refreshLabelListView(labelListView);
        } else {
            mLabel = new ArrayList<>();
        }

        for (Label label : mLabel) {
            System.out.println(">>>>>" + label.getLabelName());
        }

    }

    //回到MainActivity时刷新RecyclerView
    @Override
    protected void onResume() {
        super.onResume();
        if (mNote.size() != 0) {
            //如果已有数据且"没有更多内容"仍为显示状态，就把它隐藏掉
            tv_noMore = findViewById(R.id.no_more);
            if (tv_noMore.getVisibility() == View.VISIBLE) {
                tv_noMore.setVisibility(View.GONE);
            }
            noteListView = findViewById(R.id.note_list);
            refreshNoteListView(noteListView);
            //滑动到最后编辑的内容（太复杂，需简化）
            noteListView.scrollToPosition(notePosition == 0 ? mNote.size() : notePosition);
        }
    }

    //刷新RecyclerView，有待优化
    public void refreshNoteListView(RecyclerView recyclerView) {
        if (recyclerView != null) {
//            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));  //添加分割线
            LinearLayoutManager layoutManager = new LinearLayoutManager(this); //???
            layoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
            layoutManager.setReverseLayout(true);//列表翻转
            recyclerView.setLayoutManager(layoutManager);
            NoteAdapter2 noteAdapter2 = new NoteAdapter2(mNote, MainActivity.this);
            recyclerView.setAdapter(noteAdapter2);
            noteListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0,666,0,"删除");
                }
            });
            if (mNote.size() != 0) {
                //如果存在数据且"没有更多内容"还在，则设置为GONE
                if (tv_noMore.getVisibility() == View.VISIBLE) {
                    tv_noMore.setVisibility(View.GONE);
                }
            } else {
                if (tv_noMore.getVisibility() != View.VISIBLE) {
                    tv_noMore.setVisibility(View.VISIBLE);
                }
            }

        } else {
            Log.d(ACTIVITY_TAG, "refreshNoteListView: 传入了空的recyclerView参数");
            MyToast.makeText(this, "发生错误", Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshLabelListView(ArrayAdapter<Label> labelListAdapter, ListView labelList) {
        if (labelList != null) {
            labelListAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.label_item, mLabel);
            labelList.setAdapter(labelListAdapter);
        }
    }

    public void refreshLabelListView(ListView listView) {
        if (listView != null) {
//            ArrayAdapter<Label> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.label_item, mLabel);
            LabelAdapter adapter = new LabelAdapter(this, R.layout.label_item, mLabel);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            listView.setAdapter(adapter);
        }
    }


    //应用toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //点击toolbar的内容时启用的操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:   //点击了垃圾桶时
                if (mNote.size() == 0 && mLabel.size() == 0) {
                    AlertDialog.Builder dialog = buildAlertDialog(MainActivity.this,
                            "提示",
                            "已经没有笔记和标签了，如果列表没有刷新，请重启程序。");
                    dialog.setPositiveButton("重启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    dialog.setNegativeButton("取消", null);
                    dialog.show();
                } else {
                    AlertDialog.Builder dialog = buildAlertDialog(MainActivity.this,
                            "提示", "这个功能仅供开发者测试\n是否删除所有内容（包括标签）？");
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataSupport.deleteAll(MyNote.class);    //删除所有note
                            DataSupport.deleteAll(Label.class);     //删除所有标签
                            mNote = DataSupport.findAll(MyNote.class);  //重置mNote（可能可以省略）
                            mLabel = DataSupport.findAll(Label.class);
                            refreshNoteListView(noteListView);
                            refreshLabelListView(labelListView);
                            MyToast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("否", null);
                    dialog.show();  //显示dialog
                }
                break;

            case R.id.add_note:    //点击了添加
                Intent jumpToCreateNote = new Intent(MainActivity.this, CreateNote.class);
                startActivity(jumpToCreateNote);
                break;
            case R.id.add_label:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("请输入标签");    //设置对话框标题

                final EditText addLabel = new EditText(MainActivity.this);

                builder.setView(addLabel);
                builder.setCancelable(true);
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String labelName = addLabel.getText().toString();
                        if (labelName.length() > 10) {
                            MyToast.makeText(MainActivity.this, "标签过长", Toast.LENGTH_SHORT).show();
                        } else {
                            Label label = new Label(labelName);
                            mLabel.add(label);
                            label.save();
                            MyToast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }
                        //TODO
                        refreshLabelListView(labelListView);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;

            case android.R.id.home: //点击左上角菜单键来启动滑动菜单
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

//            以下注释的代码尚未完成
//            case R.id.user_name:
//            case R.id.useLogo:
//
//                break;

//            case R.id.nav_call:
//                if (mNote.size() != 0) {
//                    Collections.sort(mNote, new Comparator<MyNote>() {
//                        @Override
//                        public int compare(MyNote o1, MyNote o2) {
//                            int i = o1.getTitle().compareTo(o2.getTitle());
//                            if (i == 0) {
//                                return o1.getContent().compareTo(o2.getContent());
//                            }
//                            return i;
//                        }
//                    });
//                    refreshNoteListView(noteListView);
//                }
//                break;

        }
        return true;
    }

    //点击返回按钮的操作（"再按一次退出程序"）
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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

    //当点击长按菜单时要做的东西
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
    }

}

