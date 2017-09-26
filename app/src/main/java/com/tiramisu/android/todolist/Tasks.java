package com.tiramisu.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.TaskDoneAdapter;
import com.tiramisu.android.todolist.Adapter.TaskNotDoneAdapter;
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
    private RecyclerView recyclerView,recyclerView2;
    private ArrayList<TaskModel> selectedlist = new ArrayList<>();
    private ArrayList<TaskModel> unselectedlist = new ArrayList<>();
    DatabaseReference todoref,categoryref;
    @BindView(R.id.category_name) TextView categoryName;
    @BindView(R.id.addtask)
    EditText addTask2;
    private int taskCounter = 0;
    private String categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        ButterKnife.bind(this);
        categoryName.setText(getIntent().getStringExtra("category_name"));
        categoryId=getIntent().getStringExtra("category_id");
        StaticVar.CATEGORY_ID =categoryId;

  todoref = FirebaseDatabase.getInstance().getReference("Todo");
        categoryref = todoref.child(""+ StaticVar.UID+"/Categories");
        addTask=(android.support.design.widget.FloatingActionButton) findViewById(R.id.addButton);
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerView_task2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_task);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView2.setNestedScrollingEnabled(true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));







        categoryref.child(categoryId).child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                selectedlist.clear();
                unselectedlist.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    TaskModel taskModel = new TaskModel( snapshot.getKey(),snapshot.child("taskName").getValue().toString(),snapshot.child("dueDate").getValue().toString(),snapshot.child("reminder").getValue().toString(),snapshot.child("done").getValue().toString());
                    if(taskModel.getDone().equals("true"))
                    {
                        Log.d("Done","Done");
                        selectedlist.add(taskModel);
                    }
                    else {
                        Log.d("NotDone","NotDone");
                        unselectedlist.add(taskModel);
                    }
                    snapshot.getKey();


            taskCounter++;

                    if(taskCounter==dataSnapshot.getChildrenCount())
                    {
                        taskCounter=0;
                        TaskDoneAdapter mAdapter = new TaskDoneAdapter(Tasks.this, selectedlist);
                        TaskNotDoneAdapter nAdapter= new TaskNotDoneAdapter(Tasks.this,unselectedlist);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView2.setAdapter(nAdapter);


                    }



                    }



                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Tasks.this,AddNewTask.class);
                startActivity(intent);
            }
        });



        addTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Tasks.this,AddNewTask.class);
                intent.putExtra("category_id",getIntent().getStringExtra("category_id"));
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(Tasks.this,addTask2,"addtask");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(intent,activityOptionsCompat.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });





    }




}
