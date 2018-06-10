package fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyq.kaminotetest.Activity.CreateNote;
import com.example.zyq.kaminotetest.Adapter.NoteAdapter2;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.Class.MyToast;
import com.example.zyq.kaminotetest.Data.DataClass;
import com.example.zyq.kaminotetest.Data.EmotionData;
import com.example.zyq.kaminotetest.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by TALK_SWORD on 2018/3/23.
 */

public class HomeFragment extends Fragment {
    public static final String ACTIVITY_TAG = "MainActivity";  //打印日志的TAG
    private String mfrom;                                           //接收跨界面信息
    //private CallBack callBack;                                      //创建接口实例，准备回调

    private DrawerLayout mDrawerLayout;         //滑动菜单
    private TextView tv_noMore;                 //没有更多内容的文本
    private ListView labellist;                 //设置DrawerLayout点击事件
    private long mExitTime = 0;                 //记录点击返回按钮的时间
    private LinearLayout mainView;
    private Toolbar toolbar;

    public static int Color_id = 0;
    public static int notePosition;             //记录笔记位置
    public RecyclerView noteListView;           //RecyclerView 的note 列表
    public static int longClickPosition = 0;    //
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home ,null);
        tv_noMore = view.findViewById(R.id.no_more); //没有更多内容
        noteListView = view.findViewById(R.id.note_list);    //note列表
//        addLabel = getActivity().findViewById(R.id.image_add_label);  //侧边栏添加标签按钮
        mainView = view.findViewById(R.id.main_linear_layout);
        tv_noMore = view.findViewById(R.id.no_more); //没有更多内容
        //设置toolbar
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        //ToolbarController.addToolbar(toolbar);                 //加入Toolbar管理列表
        //System.out.println("HomeFragment 79"+"onCreateView>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Color_id",Context.MODE_PRIVATE);
        Color_id = sharedPreferences.getInt("id",0);
        if(Color_id != 0) {
            toolbar.setBackgroundResource(Color_id);
            }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDrawerLayout = getActivity().findViewById(R.id.drawer_layout);   //滑动菜单
//        labellist = getActivity().findViewById(R.id.label_list2);          //DrawerLayout的LabelList

        //设置toolbar的左侧菜单为显示状态
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //从数据库中读取存在的标签
        DataClass.mLabel = DataSupport.findAll(Label.class, true);
//        System.out.println("home line 95 " + DataClass.mLabel.get(0).getNotes().size());
//        DataClass.mLabel = DataSupport.findAll(Label.class, true);

        //从数据库中读取存在的笔记
        DataClass.mNote = DataSupport.findAll(MyNote.class, true);

        //判断是否读取到了笔记数据
        if (DataClass.mNote.size() != 0) {
            refreshNoteListView(noteListView);  //刷新
        } else {
            //如果读取到的内容为空，就将mNote设置为新的List，以备保存
            DataClass.mNote = new ArrayList<>();
            //将"没有更多内容"从布局显示
            tv_noMore.setVisibility(View.VISIBLE);
        }

    }

    //回到MainActivity时刷新RecyclerView
    @Override
    public void onResume() {
        super.onResume();
        //System.out.println("HomeFragment 133"+"onResume>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (DataClass.mNote.size() != 0) {
            //如果已有数据且"没有更多内容"仍为显示状态，就把它隐藏掉
            tv_noMore = getView().findViewById(R.id.no_more);
            if (tv_noMore.getVisibility() == View.VISIBLE) {
                tv_noMore.setVisibility(View.GONE);
            }
            noteListView = getView().findViewById(R.id.note_list);
            refreshNoteListView(noteListView);
            //滑动到最后编辑的内容（太复杂，需简化）
            noteListView.scrollToPosition(notePosition == 0 ? DataClass.mNote.size() : notePosition);
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
        if (recyclerView != null && getActivity() != null) {
//            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));  //添加分割线
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()); //???
            layoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
            layoutManager.setReverseLayout(true);//列表翻转
            recyclerView.setLayoutManager(layoutManager);
            NoteAdapter2 noteAdapter2 = new NoteAdapter2(DataClass.mNote, getActivity());
            recyclerView.setAdapter(noteAdapter2);
            noteListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(0,666,0,"删除");
                }
            });
            if (DataClass.mNote.size() != 0) {
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
        //System.out.println("HomeFragment 191"+"newInstance>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
                if (DataClass.mLabel.size() == 0 && DataClass.mNote.size() == 0) {//mNote.size() == 0 &&
                    AlertDialog.Builder dialog = buildAlertDialog(getActivity(),
                            "提示",
                            "已经没有笔记和标签了，如果列表没有刷新，请重启程序。");
                    dialog.setPositiveButton("重启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
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
                            DataSupport.deleteAll(EmotionData.class);       //删除所有情绪
//                            DataClass.mLabel = mLabel;
//                            MainActivity.mainActivity.refreshLabelListView(MainActivity.mainActivity.labelListView);
//                            refreshNoteListView(noteListView);
                            MyToast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    });
                    dialog.setNegativeButton("否", null);
                    dialog.show();  //显示dialog
                }
                break;

            case R.id.add_note:    //点击了添加
                Intent jumpToCreateNote = new Intent(getActivity(), CreateNote.class);
                //toolbar.setBackgroundResource(R.color.colorAccent);
                startActivity(jumpToCreateNote);
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 666:   //点击了长按后出现的删除键时
                final MyNote myNoteTemp = DataClass.mNote.get(longClickPosition);
                DataClass.mNote.get(longClickPosition).delete();    //从数据库删除
                DataClass.mNote.remove(longClickPosition);          //从笔记列表移除
                refreshNoteListView(noteListView);                  //刷新列表
                Snackbar.make(mainView, "删除成功", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DataClass.mNote.add(myNoteTemp);
                                myNoteTemp.save();
                                refreshNoteListView(noteListView);
                            }
                        }).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

}



