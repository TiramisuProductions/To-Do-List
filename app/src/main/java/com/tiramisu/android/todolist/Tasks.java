package com.tiramisu.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;


import com.tiramisu.android.todolist.Adapter.TaskAdapter;
import com.tiramisu.android.todolist.Adapter.TaskAdapter2;
import com.tiramisu.android.todolist.Model.Task;
import com.tiramisu.android.todolist.Model.WorldEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static com.tiramisu.android.todolist.Model.StaticVar.list;

public class Tasks extends AppCompatActivity {
    private android.support.design.widget.FloatingActionButton addTask;
    private RecyclerView recyclerView,recyclerView2;
    private ArrayList<Task> selectedlist = new ArrayList<>();
    private ArrayList<Task> unselectedlist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklayout);


       // dateAndTime = (ImageButton) findViewById(R.id.datetime);
      EventBus.getDefault().register(this);
        addTask=(android.support.design.widget.FloatingActionButton) findViewById(R.id.addButton);
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerView_task2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_task);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView2.setNestedScrollingEnabled(true);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(taskAdd.getWindowToken(), 0);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView2.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));



        list = Task.listAll(Task.class);
        Log.d("size", String.valueOf(list.size()));
        updaterecyler();
        ///Task.deleteAll(Task.class);


        /*dateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence dateandtime[] = new CharSequence[]{"Date", "Time"};
                AlertDialog.Builder datetime = new AlertDialog.Builder(Tasks.this);
                datetime.setTitle("Set Date and Time ");
                datetime.setItems(dateandtime, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                datetime.show();

            }
        });*/








        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Tasks.this,Search_View.class);
                startActivity(intent);
            }
        });





    }
@Subscribe
    public void onEvent(WorldEvent event){
        // your implementation
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            updaterecyler();
        }
    });

    }

    private void updaterecyler()
    {
       /* list = Task.listAll(Task.class);
        selectedlist.clear();
        unselectedlist.clear();
        for(int  i=0;i<list.size();i++)
        {
            Task task = list.get(i);
            if(task.isselected)
            {
                selectedlist.add(task);
            }
            else
            {
                unselectedlist.add(task);
            }
        }
        TaskAdapter mAdapter = new TaskAdapter(this, unselectedlist);
        TaskAdapter2 nAdapter= new TaskAdapter2(this,selectedlist);
        recyclerView.setAdapter(mAdapter);
        recyclerView2.setAdapter(nAdapter);
*/
    }



}
