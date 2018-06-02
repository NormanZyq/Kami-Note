package com.example.zyq.kaminotetest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.zyq.kaminotetest.Activity.LabelSelector;
import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.R;

import java.util.List;

/**
 * Created by zyq on 2018/3/26.
 */

public class LabelSelectorAdapter extends ArrayAdapter<Label> implements View.OnClickListener {
    private int resourceId;

    public LabelSelectorAdapter(Context context, int resource, List<Label> mLabel) {
        super(context, resource, mLabel);
        this.resourceId = resource;
    }

    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        String labelName = getItem(position).getLabelName();
        Label label = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.checkBox = view.findViewById(R.id.label_checkbox);

        viewHolder.labelName = view.findViewById(R.id.label_name);

        viewHolder.labelName.setText(label.getLabelName());     //设置标签的名字
//        viewHolder.labelName.setOnClickListener(this);

        view.setTag(position);              //设置标签所在view的tag，以其position作为tag
        viewHolder.checkBox.setTag(position);

        view.setOnClickListener(this);      //设置标签点击事件
        viewHolder.checkBox.setOnClickListener(this);

        //自动勾选这条笔记拥有的标签
        if (LabelSelector.checked[position]) {
            viewHolder.checkBox.setChecked(true);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
//        CheckBox checkBox = LayoutInflater.from(getContext()).inf
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.checkBox = v.findViewById(R.id.label_checkbox);

        if (LabelSelector.checked[(int) v.getTag()]) {
            viewHolder.checkBox.setChecked(false);
            LabelSelector.checked[(int) v.getTag()] = false;
        } else {
            viewHolder.checkBox.setChecked(true);
            LabelSelector.checked[(int) v.getTag()] = true;
        }
        System.out.println(LabelSelector.checked[0]);


    }

    private class ViewHolder {
        TextView labelName;
        CheckBox checkBox;
    }
}
