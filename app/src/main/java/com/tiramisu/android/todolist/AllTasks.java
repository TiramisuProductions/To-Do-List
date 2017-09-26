package com.tiramisu.android.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private android.support.design.widget.FloatingActionButton addTask;
    private RecyclerView recyclerView,recyclerView2;
    private ArrayList<AllTasksModel> selectedlist = new ArrayList<>();
    private ArrayList<AllTasksModel> unselectedlist = new ArrayList<>();
    DatabaseReference todoref,categoryref;
    @BindView(R.id.category_name)
    TextView categoryName;
    @BindView(R.id.addtask)
    EditText addTask2;
    private int taskCounter = 0;
    private String categoryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        ButterKnife.bind(this);
        categoryName.setText(getIntent().getStringExtra("category_name"));
        categoryId=getIntent().getStringExtra("category_id");
        todoref = FirebaseDatabase.getInstance().getReference("Todo");
        categoryref = todoref.child(""+ StaticVar.UID+"/Categories");
        addTask=(android.support.design.widget.FloatingActionButton) findViewById(R.id.addButton);
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerView_task2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_task);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView2.setNestedScrollingEnabled(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        todoref.keepSynced(true);
        categoryref.keepSynced(true);

        categoryref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                selectedlist.clear();
                unselectedlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (snapshot.child("Tasks") != null)

                    {


                        for (DataSnapshot snapshot1 : snapshot.child("Tasks").getChildren()) {
                            taskCounter++;
                            AllTasksModel allTasksModel = new AllTasksModel(snapshot.getKey().toString(), snapshot1.getKey().toString(), snapshot1.child("done").getValue().toString()
                                    , snapshot1.child("dueDate").getValue().toString(), snapshot1.child("reminder").getValue().toString(), snapshot1.child("taskName").getValue().toString());

                            if (allTasksModel.getDone().equals("true")) {
                                selectedlist.add(allTasksModel);
                            } else {
                                unselectedlist.add(allTasksModel);
                            }

                            if (taskCounter == snapshot.child("Tasks").getChildrenCount()) {
                                Log.d("Volla","Volla");
                                taskCounter = 0;
                                AllTasksDoneAdapter mAdapter = new AllTasksDoneAdapter(AllTasks.this, selectedlist);
                                AllTasksNotDoneAdapter nAdapter = new AllTasksNotDoneAdapter(AllTasks.this, unselectedlist);
                                recyclerView.setAdapter(mAdapter);
                                recyclerView2.setAdapter(nAdapter);


                            }
                        }



                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
