package com.tiramisu.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiramisu.android.todolist.Adapter.CategoriesAdapter;
import com.tiramisu.android.todolist.Adapter.TaskDoneAdapter;
import com.tiramisu.android.todolist.Adapter.TaskNotDoneAdapter;
import com.tiramisu.android.todolist.Model.CategoryModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.Model.WorldEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class Tasks extends AppCompatActivity {
    private android.support.design.widget.FloatingActionButton addTask;
    @BindView(R.id.recyclerviewdone) RecyclerView taskDoneRecylerview;
    @BindView(R.id.recyclerviewnotdone) RecyclerView taskNotDoneRecylerview;
    @BindView(R.id.categoryname) TextView categoryNameTextView;
    private String categoryId;
    private ArrayList<TaskModel> taskDoneList = new ArrayList<>();
    private ArrayList<TaskModel> taskNotDoneList = new ArrayList<>();
    private TaskDoneAdapter taskDoneAdapter;
    private TaskNotDoneAdapter taskNotDoneAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        categoryId=getIntent().getStringExtra(TAGS.CATEGORYID);
        addTask=(android.support.design.widget.FloatingActionButton) findViewById(R.id.addButton);
        taskDoneRecylerview.setNestedScrollingEnabled(true);
        taskNotDoneRecylerview.setNestedScrollingEnabled(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        taskDoneRecylerview.setLayoutManager(mLayoutManager);
        taskDoneRecylerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        taskNotDoneRecylerview.setLayoutManager(mLayoutManager2);
        taskNotDoneRecylerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        categoryNameTextView.setText(getIntent().getStringExtra(TAGS.CATEGORYNAME));

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);


getTasks();

addTask.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Tasks.this,AddNewTask.class);
        i.putExtra(TAGS.CATEGORYID,categoryId);
        startActivity(i);
    }
});

    }


    public void getTasks(){

        db.collection(TAGS.TODO).document(firebaseAuth.getUid()).collection(TAGS.CATEGORIES).document(categoryId).collection(TAGS.TASKS).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                taskDoneList.clear();
                taskNotDoneList.clear();
                for(DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()){
                    TaskModel taskModel = documentSnapshot.toObject(TaskModel.class);

                    taskModel.setTaskId(documentSnapshot.getId());
                    Log.d("qwerty", taskModel.getTaskName() + taskModel.getTaskName()+taskModel.isTaskDone());

                    if(taskModel.isTaskDone()){
                        taskDoneList.add(taskModel);
                    }
                    else {
                        taskNotDoneList.add(taskModel);
                    }

                }
                taskDoneAdapter = new TaskDoneAdapter(Tasks.this,taskDoneList,categoryId);
                taskNotDoneAdapter = new TaskNotDoneAdapter(Tasks.this,taskNotDoneList,categoryId);
                taskDoneRecylerview.setAdapter(taskDoneAdapter);
                taskNotDoneRecylerview.setAdapter(taskNotDoneAdapter);

            }
        });
    }




}
