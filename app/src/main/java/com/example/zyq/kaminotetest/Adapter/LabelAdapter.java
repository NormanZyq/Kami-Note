package com.example.zyq.kaminotetest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zyq.kaminotetest.Activity.MainActivity;
import com.example.zyq.kaminotetest.Activity.NotesForLabel;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.R;

import java.util.List;

/**
 * Created by zyq on 2018/3/26.
 */

public class LabelAdapter extends ArrayAdapter<Label> implements View.OnClickListener {
    private int resourceId;

    public LabelAdapter(Context context, int resource, List<Label> mLabel) {
        super(context, resource, mLabel);
        this.resourceId = resource;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
//        String labelName = getItem(position).getLabelName();
        Label label = getItem(position);
        View view;
        ViewHolder viewHolder = new ViewHolder();
        view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        viewHolder.labelName = view.findViewById(R.id.label_name);
        viewHolder.labelName.setText(label.getLabelName());     //设置标签的名字

//
//        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.labelName = view.findViewById(R.id.label_name);
//
//            Object[] tags = new Object[]{viewHolder, position};
//            view.setTag(tags);
//        } else {
//            view = convertView;
//            viewHolder = (ViewHolder) ((Object[]) view.getTag())[0];
//        }
//        viewHolder.labelName.setOnClickListener(this);

        view.setTag(position);                  //设置标签所在view的tag，以其position作为tag
        view.setOnClickListener(this);      //设置标签点击事件

        return view;
    }

    @Override
    public void onClick(View v) {
        int position = (int) (v.getTag());
//        System.out.println(">>>>>>>>>>>" + v.getTag());
        Intent intent = new Intent(MainActivity.mainActivity, NotesForLabel.class);
        intent.putExtra("label_name", getItem(position).getLabelName());
        v.getContext().startActivity(intent);
    }

    private class ViewHolder {
        TextView labelName;
    }
}
