package com.example.teacherapp.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacherapp.Model.Notes;
import com.example.teacherapp.NotesActivity;
import com.example.teacherapp.R;
import com.example.teacherapp.ViewModel.NotesViewModel;
import com.example.teacherapp.databinding.ActivityUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;

public class UpdateNotesActivity extends AppCompatActivity {

    ActivityUpdateNotesBinding binding;
    NotesViewModel notesViewModel;
    String priority="1";
    String  stitle, ssubtitle, sdescription, spriority;
    int iid;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Back Button Function
        imageView = findViewById(R.id.notesback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateNotesActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });


        iid = getIntent().getIntExtra("id",0);
        stitle = getIntent().getStringExtra("title");
        ssubtitle = getIntent().getStringExtra("subtitle");
        spriority = getIntent().getStringExtra("priority");
        sdescription = getIntent().getStringExtra("description");

        binding.upTitle.setText(stitle);
        binding.upSubtitle.setText(ssubtitle);
        binding.upDescription.setText(sdescription);

        switch (spriority) {
            case "1":
                binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "2":
                binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
            case "3":
                binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
                break;
        }

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

        binding.updateNotesBtn.setOnClickListener(v -> {

            String title = binding.upTitle.getText().toString();
            String subtitle = binding.upSubtitle.getText().toString();
            String description = binding.upDescription.getText().toString();

            UpdateNotes(title,subtitle,description);

        });
    }

    private void UpdateNotes(String title, String subtitle, String description) {
        Date date= new Date();
        CharSequence sequence= DateFormat.format("MMMM d, yyyy",date.getTime());

        Notes updateNotes = new Notes();

        updateNotes.id= iid;
        updateNotes.notesTitle= title;
        updateNotes.notesSubtitle= subtitle;
        updateNotes.notes= description;
        updateNotes.notesDate= sequence.toString();
        updateNotes.notesPriority= priority;

        notesViewModel.updateNote(updateNotes);

        Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.deletenote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.ic_deleteNote){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this);

            View view = LayoutInflater.from(UpdateNotesActivity.this).
                    inflate(R.layout.delete_bottom_shape,(LinearLayout)findViewById(R.id.bottomSheet));

            sheetDialog.setContentView(view);

            TextView yes,no;
            yes= view.findViewById(R.id.deleteYes);
            no= view.findViewById(R.id.deleteNo);

            yes.setOnClickListener(v -> {

                notesViewModel.deleteNote(iid);
                finish();

            });
            no.setOnClickListener(v -> {
                sheetDialog.dismiss();
            });
            sheetDialog.show();
        }
        return true;
    }
}