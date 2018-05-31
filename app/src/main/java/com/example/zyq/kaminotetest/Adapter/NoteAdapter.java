package com.example.zyq.kaminotetest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zyq.kaminotetest.Activity.EditNote;
import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.R;

import java.util.List;

/**
 * Created by zyq on 2018/2/3.
 * 这是Note在RecyclerView中的样式
 *
 * 已弃用，现使用NoteAdapter2
 *
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<MyNote> mNoteList;
    private Context context;

    public NoteAdapter(List<MyNote> myNotes, Context context) {
        this.mNoteList = myNotes;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View noteView;
        TextView noteTitle;
        TextView noteContent;
        TextView noteEditedDate;

        public ViewHolder(View itemView) {
            super(itemView);
            noteView = itemView;
            noteTitle = itemView.findViewById(R.id.note_title); //设置标题显示栏
            noteContent = itemView.findViewById(R.id.note_content); //内容显示栏
            noteEditedDate = itemView.findViewById(R.id.note_edited_date);
        }
    }

    public NoteAdapter(List<MyNote> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //创建点击响应事件
        holder.noteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                //在点击项目的时候跳转到编辑页
                Intent jumpToEditNote = new Intent(view.getContext(), EditNote.class);
                jumpToEditNote.putExtra("note_position", position);
                view.getContext().startActivity(jumpToEditNote);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        MyNote note = mNoteList.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());
        holder.noteEditedDate.setText(note.getLastEdited());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
