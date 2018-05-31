package com.example.zyq.kaminotetest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zyq.kaminotetest.Activity.EditNote;
import com.example.zyq.kaminotetest.Activity.MainActivity;
import com.example.zyq.kaminotetest.Class.MyNote;
import com.example.zyq.kaminotetest.R;

import java.util.List;

/**
 * Created by zyq on 2018/3/2.
 * 来源：CSDN，作者信息已忘，向原作者致歉
 */

public class NoteAdapter2 extends RecyclerView.Adapter<NoteAdapter2.NotesViewHolder> {

    private List<MyNote> myNotes;
    private Context context;

    public NoteAdapter2(List<MyNote> myNotes, Context context) {
        this.myNotes = myNotes;
        this.context = context;
    }

    //自定义ViewHolder类
    static class NotesViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView noteTitle;
        TextView noteContent;
        TextView editedDate;

        public NotesViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            noteTitle = itemView.findViewById(R.id.note_title2);
            noteContent = itemView.findViewById(R.id.note_content2);
            editedDate = itemView.findViewById(R.id.note_edited_date2);
            noteTitle.setBackgroundColor(Color.argb(20, 0, 0, 0));
            noteTitle.setBackgroundResource(R.drawable.corner_view);
        }
    }


    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.note_card_item, viewGroup, false);
        final NotesViewHolder notesViewHolder = new NotesViewHolder(view);
//        final NoteAdapter.ViewHolder holder = new NoteAdapter.ViewHolder(view);
        //创建点击响应事件
        notesViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = notesViewHolder.getAdapterPosition();
                MainActivity.notePosition = position;
                //在点击项目的时候跳转到编辑页
                Intent jumpToEditNote = new Intent(view.getContext(), EditNote.class);
                jumpToEditNote.putExtra("note_position", position);
                view.getContext().startActivity(jumpToEditNote);
            }
        });

        //创建长按事件
        notesViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Snackbar.make(view, "长按事件：", Snackbar.LENGTH_SHORT).show();
                MainActivity.longClickPosition = notesViewHolder.getAdapterPosition();
                return false;
            }
        });

        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        MyNote note = myNotes.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());
        holder.editedDate.setText(note.getLastEdited());
    }

    //获得当前列表笔记数目
    @Override
    public int getItemCount() {
        return myNotes.size();
    }


}
