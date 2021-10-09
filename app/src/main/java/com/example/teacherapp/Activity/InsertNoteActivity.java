package com.example.teacherapp.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teacherapp.MainActivity;
import com.example.teacherapp.Model.Notes;
import com.example.teacherapp.NotesActivity;
import com.example.teacherapp.R;
import com.example.teacherapp.ViewModel.NotesViewModel;
import com.example.teacherapp.databinding.ActivityInsertNoteBinding;

import org.xml.sax.ErrorHandler;

import java.util.Date;


public class InsertNoteActivity extends AppCompatActivity {

    ActivityInsertNoteBinding binding;
    String title,subtitle,description;
    NotesViewModel notesViewModel;
    String priority= "1";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityInsertNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Back Button Function
        imageView = findViewById(R.id.notesback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertNoteActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.greenPriority.setOnClickListener(v -> {

            binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);

            priority= "1";
        });

        binding.yellowPriority.setOnClickListener(v -> {
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.redPriority.setImageResource(0);

            priority= "2";
        });

        binding.redPriority.setOnClickListener(v -> {
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);

            priority= "3";
        });

        binding.DoneNotesBtn.setOnClickListener(v-> {

            title = binding.notesTitle.getText().toString();
            subtitle = binding.notesSubtitle.getText().toString();
            description = binding.notesDescription.getText().toString();

            if (title.isEmpty()) {
                binding.notesTitle.requestFocus();
                binding.notesTitle.setError("Field cannot be Empty");
                //Toast.makeText(getApplicationContext(),"enter title",Toast.LENGTH_SHORT).show();
            }

            else{
            CreateNotes(title,subtitle,description);
            }

        });
    }

    private void CreateNotes(String title, String subtitle, String description) {

        Date date= new Date();
        CharSequence sequence= DateFormat.format("MMMM d, yyyy",date.getTime());

        Notes notes1 = new Notes();
        notes1.notesTitle= title;
      /*  if(notes1.notesTitle.length()==0)
        {
            Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
        } */
        notes1.notesSubtitle= subtitle;
        notes1.notes= description;
        notes1.notesDate= sequence.toString();
        notes1.notesPriority= priority;


        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Note created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}