package com.example.teacherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.teacherapp.Activity.InsertNoteActivity;
import com.example.teacherapp.Adapter.NotesAdapter;
import com.example.teacherapp.Model.Notes;
import com.example.teacherapp.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    FloatingActionButton newNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecyclerview;
    NotesAdapter adapter;

    TextView nofilter, hightolow, lowtohigh;
    List<Notes> filternotesallList;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        newNotesBtn = findViewById(R.id.newNotesBtn);
        notesRecyclerview = findViewById(R.id.notesRecyclerview);

        //Back Button Function
        imageView = findViewById(R.id.notesback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        nofilter = findViewById(R.id.noFilter);
        hightolow = findViewById(R.id.hightolow);
        lowtohigh = findViewById(R.id.lowtohigh);

        nofilter.setBackgroundResource(R.drawable.filter_selected_shape);

        nofilter.setOnClickListener(view -> {
            loadData(0);
            hightolow.setBackgroundResource(R.drawable.filter_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_shape);
            nofilter.setBackgroundResource(R.drawable.filter_selected_shape);
        });
        hightolow.setOnClickListener(view -> {
            loadData(1);
            hightolow.setBackgroundResource(R.drawable.filter_selected_shape);
            nofilter.setBackgroundResource(R.drawable.filter_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_shape);
        });
        lowtohigh.setOnClickListener(view -> {
            loadData(2);
            nofilter.setBackgroundResource(R.drawable.filter_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_selected_shape);
            hightolow.setBackgroundResource(R.drawable.filter_shape);
        });

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotesBtn.setOnClickListener(v -> {
            startActivity(new Intent(NotesActivity.this, InsertNoteActivity.class));
        });

        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filternotesallList= notes;
            }
        });
    }

    private void loadData(int i) {
        if(i==0){
            notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesallList= notes;
                }
            });
        } else if(i==1){
            notesViewModel.hightolow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesallList= notes;
                }
            });
        } else if(i==2){
            notesViewModel.lowtohigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesallList= notes;
                }
            });
        }}

    public void setAdapter(List<Notes> notes) {
        notesRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(NotesActivity.this, notes);
        notesRecyclerview.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes, menu);

        MenuItem menuItem= menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search notes...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {
        //Log.e("0000", "NotesFilter: "+newText );

        ArrayList<Notes> FilterNames = new ArrayList<>();

        for(Notes notes:this.filternotesallList){
            if(notes.notesTitle.contains(newText) || notes.notesSubtitle.contains(newText)){
                FilterNames.add(notes);
            }
        }
        this.adapter.searchNotes(FilterNames);

    }
}
