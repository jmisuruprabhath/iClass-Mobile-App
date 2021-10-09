package com.example.teacherapp.Adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teacherapp.Activity.UpdateNotesActivity;
import com.example.teacherapp.Model.Notes;
import com.example.teacherapp.NotesActivity;
import com.example.teacherapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder>{

    NotesActivity notesActivity;
    List<Notes> notes;
    List<Notes> allNotesItem;

    public NotesAdapter(NotesActivity notesActivity, List<Notes> notes) {
        this.notesActivity = notesActivity;
        this.notes = notes;
        allNotesItem= new ArrayList<>(notes);
    }

    public void searchNotes(List<Notes> filteredName){

        this.notes= filteredName;
        notifyDataSetChanged();

    }

    //@NonNull
    @Override
    public notesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(notesActivity).inflate(R.layout.item_notes,parent,false));
    }

    @Override
    public void onBindViewHolder(notesViewHolder holder, int position) {
        Notes note= notes.get(position);

        switch (note.notesPriority) {
            case "1":
                holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
                break;
            case "2":
                holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
                break;
            case "3":
                holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
                break;
        }


        holder.title.setText(note.notesTitle);
        holder.subtitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v-> {
            Intent intent= new Intent(notesActivity, UpdateNotesActivity.class);
            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subtitle",note.notesSubtitle);
            intent.putExtra("priority",note.notesPriority);
            intent.putExtra("description",note.notes);
            notesActivity.startActivity(intent);


        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class notesViewHolder extends RecyclerView.ViewHolder {

        TextView title,subtitle,notesDate;
        View notesPriority;
        public notesViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notesTitle);
            subtitle = itemView.findViewById(R.id.notesSubtitle);
            notesDate = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
}
