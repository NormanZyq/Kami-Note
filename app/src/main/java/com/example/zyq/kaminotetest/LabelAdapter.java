package com.example.zyq.kaminotetest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zyq on 2018/3/20.
 */

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.LabelsViewHolder>{
    private List<Label> mLabel;
    private Context context;

    public LabelAdapter(List<Label> mLabel, Context context) {
        this.mLabel = mLabel;
        this.context = context;
    }

    static class LabelsViewHolder extends RecyclerView.ViewHolder {
        View labelView;
        TextView labelName;

        public LabelsViewHolder(View itemView) {
            super(itemView);
            labelView = itemView;
            labelName = itemView.findViewById(R.id.label_name);
        }
    }



    @Override
    public LabelsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.label_item, viewGroup, false);
        final LabelsViewHolder labelsViewHolder = new LabelsViewHolder(view);

        //点击事件
        labelsViewHolder.labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        return labelsViewHolder;
    }

    @Override
    public void onBindViewHolder(LabelsViewHolder holder, int position) {
        Label label = mLabel.get(position);
        holder.labelName.setText(label.getLabelName());
    }

    @Override
    public int getItemCount() {
        return mLabel.size();
    }



}
