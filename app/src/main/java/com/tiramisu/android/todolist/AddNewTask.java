package com.tiramisu.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.tiramisu.android.todolist.Adapter.TaskSearchAdapter;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.Model.TaskSuggestionModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public  class AddNewTask extends AppCompatActivity {

    private TaskSearchAdapter taskSearchAdapter;
    private ImageView done;
    private ArrayList<TaskSuggestionModel> taskSuggestionList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String categoryId;
    @BindView(R.id.task) EditText taskEditText;
    @BindView(R.id.today) Button today;
    @BindView(R.id.tomorrow) Button tomorrowButton;
    @BindView(R.id.upcoming) Button upcomingButton;
    @BindView(R.id.custom) Button customButton;
    @BindView(R.id.tasksearch) RecyclerView taskSearchRecylerView;
    @BindView(R.id.datetime) RelativeLayout dateTimeLayout;
    @BindView(R.id.done) ImageView doneImageView;




    SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");


    public AddNewTask() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        ButterKnife.bind(this);

        categoryId = getIntent().getStringExtra(TAGS.CATEGORYID);


        taskSearchRecylerView.setHasFixedSize(true);
        taskSearchRecylerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


        taskEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dateTimeLayout.setVisibility(View.VISIBLE);
            }
        });


       ;


        //Pushes taskname,date and time into the database
        doneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        if(TextUtils.isEmpty(taskEditText.getText().toString().trim())){
            taskEditText.setError("Task Cannot Be Empty");
            return;
        }

                TaskModel task = new TaskModel(taskEditText.getText().toString().trim(),false);
                db.collection(TAGS.TODO).document(firebaseAuth.getUid()).collection(TAGS.CATEGORIES).document(categoryId).collection(TAGS.TASKS).add(task);
                finish();




            }
        });



    }





    //Taken date and time is set here

}





