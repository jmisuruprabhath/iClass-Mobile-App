/* package com.example.teacherapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

class attendanceAdapter extends FirebaseRecyclerAdapter<attendanceModel, attendanceAdapter.AttendanceViewholder> {

    //private FirebaseRecyclerOptions<attendanceModel> options;
    public static int[] plots = new int[]{4, 1, 2, 3, 4, 5, 6, 7, 2, 4, 5, 1,12};
    //plots= new int[13];

    private Context context;


    public attendanceAdapter(FirebaseRecyclerOptions<attendanceModel> options, Context c) {
        super(options);
        this.context = c;
    }

    @NonNull
    @Override
    public AttendanceViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student, parent, false);
        return new attendanceAdapter.AttendanceViewholder(view);
    }


    static class AttendanceViewholder extends RecyclerView.ViewHolder {
        public TextView t;

        public AttendanceViewholder(@NonNull View itemView) {
            super(itemView);
            t = itemView.findViewById(R.id.studentName);

        }
    }
}


*/
