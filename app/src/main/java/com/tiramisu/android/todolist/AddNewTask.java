package com.tiramisu.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.CategorySearchAdapter;
import com.tiramisu.android.todolist.Adapter.TaskSearchAdapter;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.Model.TaskSuggestionModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public  class AddNewTask extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{

    RecyclerView recyclerView;
    private EditText editSearch;
    private TaskSearchAdapter taskSearchAdapter;
    private ImageView done;
    DatabaseReference taskSugeestionRef;
    private ArrayList<String> search_list = new ArrayList<String>();
    private ArrayList<String> search_list2 = new ArrayList<String>();
    private ArrayList<TaskSuggestionModel> taskSuggestionList = new ArrayList<>();
    int counter =0;
    private Button today,tomorrow,upcoming,custom;
    private RelativeLayout search_linear;
    long duetime,date;

    boolean button1=false,button2=false,button3=false,button4=false,time=false;
    SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DatabaseReference CategoryRef;


    public AddNewTask() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__view);



        CategoryRef = FirebaseDatabase.getInstance().getReference("Todo/"+ StaticVar.UID + "/Categories");

        taskSugeestionRef= FirebaseDatabase.getInstance().getReference("category_suggetion_list");

        search_linear=(RelativeLayout) findViewById(R.id.search_ll);

        done=(ImageView) findViewById(R.id.done);
        editSearch = (EditText) findViewById(R.id.addtask);
        today=(Button)findViewById(R.id.today);
        tomorrow=(Button)findViewById(R.id.tomorrow);
        upcoming=(Button)findViewById(R.id.upcoming);
        custom=(Button)findViewById(R.id.custom);
        recyclerView=(RecyclerView)findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        addTextListener();
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_linear.setVisibility(View.VISIBLE);
            }
        });


        taskSugeestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child("catSugList").getChildren()){

                    counter++;
                    Log.d("chair8",snapshot.getValue().toString());
                    //firebase data
                    taskSuggestionList.add(new TaskSuggestionModel(snapshot.getValue().toString()));

                    long l = dataSnapshot.child("catSugList").getChildrenCount();
                    Log.d("chair81",""+l);

                    if(counter == dataSnapshot.child("catSugList").getChildrenCount())
                    {




                        taskSearchAdapter = new TaskSearchAdapter(taskSuggestionList, AddNewTask.this);
                        recyclerView.setAdapter(taskSearchAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String taskName = editSearch.getText().toString().trim();




                Log.d("hello",""+date);
                Log.d("hello1",""+duetime);

                String key_id1 = CategoryRef.push().getKey();
                TaskModel taskModel = new TaskModel(taskName,"temp","temp","false");
                CategoryRef.child(getIntent().getStringExtra("category_id")).child("Tasks").child(key_id1).setValue(taskModel);


                finish();
            }
        });


        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddNewTask.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(),"time");

              //  now.getTimeInMillis();
                button1=true;
            }
        });

        tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddNewTask.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.show(getFragmentManager(),"time");
                button2=true;
            }
        });


        upcoming.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                now.getTimeInMillis();

                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        AddNewTask.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(),"date");
                button3=true;

            }


        });


        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                now.getTimeInMillis();

                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddNewTask.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(),"date");
                button4=true;




            }
        });

    }

    private void addTextListener() {

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                charSequence = charSequence.toString().toLowerCase();

                final ArrayList<TaskSuggestionModel> filterList = new ArrayList<>();

                for(int j = 0; j < taskSuggestionList.size(); j++){

                    final String text = taskSuggestionList.get(j).getSuggestion().toLowerCase();
                    if(text.contains(charSequence)){
                        filterList.add(taskSuggestionList.get(j));
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(AddNewTask.this));
                taskSearchAdapter = new TaskSearchAdapter(filterList,AddNewTask.this);
                recyclerView.setAdapter(taskSearchAdapter);
            }
        });
    }




    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        if(button1=true) {
            duetime =  hourOfDay + minute + second;
        }

        if(button2=true)
        {
            Calendar c=Calendar.getInstance();
            c.getTimeInMillis();
            int day=c.get(Calendar.DAY_OF_MONTH);
            ++ day;
            duetime = Long.valueOf("You picked the following time" + hourOfDay + minute + second+ day);

        }

        if(time=true){

            duetime = Long.valueOf(("You picked the following time" + hourOfDay + minute + second));
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if(button3=true) {

           String duedate = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            Date D=null;
            try {
                D=myFormat.parse(duedate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date = D.getTime();


        }
        if(button4=true)
        {
         String   duedate = +dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            Date D=null;
            try {
                D=myFormat.parse(duedate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date = D.getTime();

            Calendar now =  Calendar.getInstance();
            TimePickerDialog tpd = TimePickerDialog.newInstance(
                    AddNewTask.this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
            );
            tpd.show(getFragmentManager(), "time");

            time=true;
            button4=false;



        }
    }
}





