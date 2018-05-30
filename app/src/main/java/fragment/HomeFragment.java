package fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyq.kaminotetest.CreateNote;
import com.example.zyq.kaminotetest.Label;
import com.example.zyq.kaminotetest.LabelAdapter;
import com.example.zyq.kaminotetest.MainActivity;
import com.example.zyq.kaminotetest.MyNote;
import com.example.zyq.kaminotetest.MyToast;
import com.example.zyq.kaminotetest.NoteAdapter2;
import com.example.zyq.kaminotetest.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TALK_SWORD on 2018/3/23.
 */

public class HomeFragment extends Fragment {
    private static final String ACTIVITY_TAG = "MainActivity";  //打印日志的TAG
    private BottomNavigationView bottomNavigationView;            //底部栏引用
    private Fragment[] fragments;                                  //布局管理列表
    private String mfrom;                                           //接收跨界面信息
    //private CallBack callBack;                                      //创建接口实例，准备回调

    private DrawerLayout mDrawerLayout;         //滑动菜单
    private TextView tv_noMore;                 //没有更多内容的文本
    private long mExitTime = 0;                 //记录点击返回按钮的时间
    private LinearLayout mainView;
    private LabelAdapter labelListAdapter;
    private ImageView settings;

    public static List<MyNote> mNote;           //保存note的列表
    public static List<Label> mLabel;           //
    public static int notePosition;             //记录笔记位置
    public RecyclerView noteListView;           //RecyclerView 的note 列表
    public static int longClickPosition = 0;    //
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home ,null);
        tv_noMore = view.findViewById(R.id.no_more); //没有更多内容
        noteListView = view.findViewById(R.id.note_list);    //note列表
        settings = getActivity().findViewById(R.id.image_settings);  //侧边栏设置按钮
        mainView = view.findViewById(R.id.main_linear_layout);
        tv_noMore = view.findViewById(R.id.no_more); //没有更多内容
        noteListView = view.findViewById(R.id.note_list);    //note列表
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //设置toolbar
        Toolbar toolbar = this.getView().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        mDrawerLayout = getActivity().findViewById(R.id.drawer_layout);   //滑动菜单

        //设置toolbar的左侧菜单为显示状态
        ActionBar actionBar = MainActivity.mainActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //从数据库中读取存在的笔记
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
            MainActivity.mainActivity.refreshLabelListView(MainActivity.mainActivity.labelListView);

        } else {
            mLabel = new ArrayList<>();
        }

        for (Label label : mLabel) {
            System.out.println(">>>>>" + label.getLabelName());
        }


        //从数据库中读取存在的笔记
        mNote = DataSupport.findAll(MyNote.class);
        //判断是否读取到了数据
        if (mNote.size() != 0) {
            refreshNoteListView(noteListView);  //刷新
        } else {
            //如果读取到的内容为空，就将mNote设置为新的List，以备保存
            mNote = new ArrayList<>();
            //将"没有更多内容"从布局显示
            tv_noMore.setVisibility(View.VISIBLE);
        }

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"settings",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //回到MainActivity时刷新RecyclerView
    @Override
    public void onResume() {
        super.onResume();
        if (mNote.size() != 0) {
            //如果已有数据且"没有更多内容"仍为显示状态，就把它隐藏掉
            tv_noMore = getView().findViewById(R.id.no_more);
            if (tv_noMore.getVisibility() == View.VISIBLE) {
                tv_noMore.setVisibility(View.GONE);
            }
            noteListView = getView().findViewById(R.id.note_list);
            refreshNoteListView(noteListView);
            //滑动到最后编辑的内容（太复杂，需简化）
            noteListView.scrollToPosition(notePosition == 0 ? mNote.size() : notePosition);
        }
    }

    public void refreshLabelListView(ListView listView) {
        if (listView != null) {
            LabelAdapter adapter = new LabelAdapter(getActivity(), R.layout.label_item, mLabel);
            listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            listView.setAdapter(adapter);
        }
    }


    //应用toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar,menu);
    }

    //刷新RecyclerView，有待优化
    public void refreshNoteListView(RecyclerView recyclerView) {
        if (recyclerView != null&&getActivity() != null) {
//            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));  //添加分割线
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //???
            layoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
            layoutManager.setReverseLayout(true);//列表翻转
            recyclerView.setLayoutManager(layoutManager);
            NoteAdapter2 noteAdapter2 = new NoteAdapter2(mNote, getActivity());
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
            MyToast.makeText(getActivity(), "发生错误", Toast.LENGTH_SHORT).show();
        }
    }

    //创建Fragment实例
    public static HomeFragment newInstance(String from){
        HomeFragment fragment = new HomeFragment();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:   //点击了垃圾桶时
                if (mLabel.size() == 0) {//mNote.size() == 0 &&
                    AlertDialog.Builder dialog = buildAlertDialog(getActivity(),
                            "提示",
                            "已经没有笔记和标签了，如果列表没有刷新，请重启程序。");
                    dialog.setPositiveButton("重启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.mainActivity.finish();
                        }
                    });
                    dialog.setNegativeButton("取消", null);
                    dialog.show();
                } else {
                    AlertDialog.Builder dialog = buildAlertDialog(getActivity(),
                            "提示", "这个功能仅供开发者测试\n是否删除所有内容（包括标签）？");
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataSupport.deleteAll(MyNote.class);    //删除所有note
                            DataSupport.deleteAll(Label.class);     //删除所有标签
                            mNote = DataSupport.findAll(MyNote.class);  //重置mNote（可能可以省略）
                            mLabel = DataSupport.findAll(Label.class);
                            MainActivity.mLabel = mLabel;
                            MainActivity.mainActivity.refreshLabelListView(MainActivity.mainActivity.labelListView);
                            refreshNoteListView(noteListView);
                            MyToast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("否", null);
                    dialog.show();  //显示dialog
                }
                break;

            case R.id.add_note:    //点击了添加
                Intent jumpToCreateNote = new Intent(getActivity(), CreateNote.class);
                startActivity(jumpToCreateNote);
                break;
            case R.id.add_label:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入标签");    //设置对话框标题

                final EditText addLabel = new EditText(getActivity());

                builder.setView(addLabel);
                builder.setCancelable(true);
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String labelName = addLabel.getText().toString();
                        if (labelName.length() > 10) {
                            MyToast.makeText(getActivity(), "标签过长", Toast.LENGTH_SHORT).show();
                        } else {
                            Label label = new Label(labelName);
                            mLabel.add(label);
                            label.save();
                            MyToast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                        }
                        //TODO
                        MainActivity.mLabel = mLabel;
                        ((MainActivity)getActivity()).refreshLabelListView(MainActivity.mainActivity.labelListView);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
                break;

            case android.R.id.home: //点击左上角菜单键来启动滑动菜单
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

        }
        return true;
    }

    public static AlertDialog.Builder buildAlertDialog(Context context, String alertTitle,
                                                       String alertMessage) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(alertTitle);
        dialog.setMessage(alertMessage);
        return dialog;
    }
}



