package com.tiramisu.android.todolist;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.AllTasksDoneAdapter;
import com.tiramisu.android.todolist.Adapter.AllTasksNotDoneAdapter;
import com.tiramisu.android.todolist.Adapter.TaskDoneAdapter;
import com.tiramisu.android.todolist.Adapter.TaskNotDoneAdapter;
import com.tiramisu.android.todolist.Model.AllTasksModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sarvesh Palav on 25-09-2017.
 */

public class AllTasks extends AppCompatActivity {
    @BindView(R.id.addTask) RecyclerView addTaskFloatingActionButton;
    @BindView(R.id.notdone) RecyclerView notDoneRecylerView;
    @BindView(R.id.done) RecyclerView doneRecylerView;
    @BindView(R.id.category_name) TextView categoryName;

    private int taskCounter = 0;
    private String categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        ButterKnife.bind(this);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());





    }
}
