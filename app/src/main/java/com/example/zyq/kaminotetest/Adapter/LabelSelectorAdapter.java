package com.example.zyq.kaminotetest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zyq.kaminotetest.Class.Label;
import com.example.zyq.kaminotetest.R;

import java.util.List;

/**
 * Created by zyq on 2018/3/26.
 */

public class LabelSelectorAdapter extends ArrayAdapter<Label> {
    private int resourceId;

    public LabelSelectorAdapter(Context context, int resource, List<Label> mLabel) {
        super(context, resource, mLabel);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        String labelName = getItem(position).getLabelName();
        Label label = getItem(position);
        View view;
        ViewHolder viewHolder;
        //对view循环使用
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.labelName = view.findViewById(R.id.label_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.labelName.setText(label.getLabelName());     //设置标签的名字
//        viewHolder.labelName.setOnClickListener(this);

        view.setTag(position);              //设置标签所在view的tag，以其position作为tag
//        view.setOnClickListener(this);      //设置标签点击事件

        return view;
    }

    private class ViewHolder {
        TextView labelName;
    }
}
