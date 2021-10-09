package com.example.teacherapp;

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


class classAdapter extends FirebaseRecyclerAdapter<classModel, classAdapter.classViewholder> {

    private Context context;


    private FirebaseUser mUser;
    private String onlineUserID;
    private DatabaseReference reference;


    public classAdapter(@NonNull FirebaseRecyclerOptions<classModel> options, ArrayList<classModel> cArrayList, Context context) {

        super(options);
        this.context = context;
    }

    public classAdapter(FirebaseRecyclerOptions<classModel> options) {
        super(options);
    }


    // Function to bind the view in Card view(here
    // "person.xml") iwth data ins
    // model class(here "person.class")
    @Override
    protected void onBindViewHolder(@NonNull classViewholder holder, int position, @NonNull classModel model) {

        holder.name.setText(model.getName());

        //holder.name.setText("testingBn");

        holder.description.setText(model.getDescription());
        //holder.description.setText("Hello World!");

        //holder.batch.setText(model.getBatch());
        //holder.time.setText(model.getTime());

        //cArrayList.add(model);

        //FirebaseValues
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        onlineUserID = null;
        if (mUser != null) {
            onlineUserID = mUser.getUid();
        } else {
            Toast.makeText(context, "Authentication Error", Toast.LENGTH_LONG).show();
            return;
        }
        reference = FirebaseDatabase.getInstance().getReference().child("classes").child(onlineUserID);


        holder.dotClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(holder.dotClass.getContext(), holder.dotClass);
                //inflating menu from xml resource
                popup.inflate(R.menu.popup_menu_class);
                //adding click listener
                Context context = holder.dotClass.getContext();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.moreInfo:
                                showMoreInfo(view, context, model);
                                return true;
                            case R.id.updateClass:
                                updateClass(view, context, model);
                                return true;
                            case R.id.deleteClass:
                                deleteClass(context, model);
                                context.startActivity(new Intent(context,classView.class));
                                return true;
                            default:
                                return false;
                        }
                    }

                });
                //displaying the popup
                popup.show();

            }

        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //adding click listener
                Context context = holder.dotClass.getContext();

                Toast.makeText(context,"Working",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, Attendance.class);
                passModel m = new passModel(model,reference);
                context.startActivity(intent);

            }

        });
    }


    // Function to tell the class about the Card view (here
    // which the data will be shown
    @NonNull
    @Override
    public classViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new classAdapter.classViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    static class classViewholder extends RecyclerView.ViewHolder {
        public TextView name, description, batch, time;
        View viewClass;
        public TextView dotClass;

        public classViewholder(@NonNull View itemView) {
            super(itemView);
            viewClass = itemView;

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.desc);
            //batch = itemView.findViewById(R.id.batchRow);
            //time = itemView.findViewById(R.id.timeRow);
            dotClass = viewClass.findViewById(R.id.dotClass);


        }
    }



    private void showMoreInfo(View view, Context context, classModel model) {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.more_info, null);
        myDialog.setView(myView);

        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();

        TextView name = myView.findViewById(R.id.name);
        TextView description = myView.findViewById(R.id.desc);
        TextView batch = myView.findViewById(R.id.batch);
        TextView time = myView.findViewById(R.id.time);
        Button close = myView.findViewById(R.id.closeBtn);

        name.setText(model.getName());
        description.setText(model.getDescription());
        batch.setText("Batch: "+model.getBatch());
        time.setText("Time: "+model.getTime());


        //cancel button
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }




    private void updateClass(View view, Context context, classModel model) {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View myView = inflater.inflate(R.layout.update_class, null);
        myDialog.setView(myView);

        AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();


        EditText name = myView.findViewById(R.id.className);
        EditText description = myView.findViewById(R.id.classDescription);
        EditText batch = myView.findViewById(R.id.batch);
        EditText time = myView.findViewById(R.id.timeInput);
        Button save = myView.findViewById(R.id.saveClass);
        Button cancel = myView.findViewById(R.id.addClass);

        name.setText(model.getName(), TextView.BufferType.EDITABLE);
        description.setText(model.getDescription(), TextView.BufferType.EDITABLE);
        batch.setText(model.getBatch(), TextView.BufferType.EDITABLE);
        time.setText(model.getTime(), TextView.BufferType.EDITABLE);


        //cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //update Button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nClass = name.getText().toString().trim();
                String nDescription = description.getText().toString().trim();
                String nBatch = batch.getText().toString().trim();
                String nTime = time.getText().toString().trim();

                String id = model.getId();  //get the id of the class which we needs to edit

                classModel updateClass = new classModel(nClass, nDescription, nBatch, nTime, id);  //set new values to the model class

                assert id != null;

                Map<String, Object> updateClassMap = updateClass.toMap();

                //update class using firebase reference
                reference.child(id).updateChildren(updateClassMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Class Updated Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            String err = task.getException().toString();
                            Toast.makeText(context, "Class Update Failed!" + err, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();

            }
        });
        dialog.show();
    }


    private void deleteClass(Context context, classModel model) {
        reference.child(model.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                deleteAttendance(model.getName());
                Toast.makeText(context, "Class Deleted Successfully", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error in deletion ", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
        });
    }

    private void deleteAttendance(String name) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String onlineUserID = null;
        if (mUser != null) {
            onlineUserID = mUser.getUid();
        } else {
            //Toast.makeText(context, "Error Attendance authentication", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("attendance").child(onlineUserID)
                .child(name);

        ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               //Toast.makeText(context, "Class Deleted Successfully", Toast.LENGTH_LONG).show();

                //context.startActivity(new Intent(classView.this,MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(context, "Error in deletion ", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }


}