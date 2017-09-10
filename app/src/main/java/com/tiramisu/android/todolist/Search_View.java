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
import android.widget.RelativeLayout;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.SearchAdapter;
import com.tiramisu.android.todolist.Model.AutoFill;
import com.tiramisu.android.todolist.Model.CatSuggested;
import com.tiramisu.android.todolist.Model.Task;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public  class Search_View extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{

    RecyclerView recyclerView;
    DatabaseReference mDatabase,auto;
    private EditText editSearch;
    private RecyclerView.Adapter mAdapter;
    private Button Enter;
    public List<AutoFill> autofillist=new ArrayList<>();
    DatabaseReference searchlist;
    private ArrayList<String> search_list = new ArrayList<String>();
    private ArrayList<String> search_list2 = new ArrayList<String>();
    int counter =0;
    private Button today,tomorrow,upcoming,custom;
    private RelativeLayout search_linear;
    long duetime,date;
    long catid;
    boolean button1=false,button2=false,button3=false,button4=false,time=false;
    SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Search_View() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__view);
        catid = getIntent().getLongExtra("id",1);
        CatSuggested.deleteAll(CatSuggested.class);

        searchlist= FirebaseDatabase.getInstance().getReference("category_suggetion_list");

        search_linear=(RelativeLayout) findViewById(R.id.search_ll);

        Enter=(Button)findViewById(R.id.enter);
        editSearch = (EditText) findViewById(R.id.search);
        today=(Button)findViewById(R.id.today);
        tomorrow=(Button)findViewById(R.id.tomorrow);
        upcoming=(Button)findViewById(R.id.upcoming);
        custom=(Button)findViewById(R.id.custom);
        recyclerView=(RecyclerView)findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        suggestionList();

        addTextListener();
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_linear.setVisibility(View.VISIBLE);
            }
        });

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String task = editSearch.getText().toString().trim();

                Log.d("freeze",""+catid);

                Task task1 = new Task(catid,task,null,null,0,date,duetime,true);
                task1.save();
                Log.d("hello",""+date);
                Log.d("hello1",""+duetime);

                Intent intent=new Intent(Search_View.this,Tasks.class);

                startActivity(intent);
                finish();
            }
        });


        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        Search_View.this,
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
                        Search_View.this,
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
                        Search_View.this,
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
                        Search_View.this,
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

                final ArrayList<String> filterList = new ArrayList<String>();

                for(int j = 0; j < search_list2.size(); j++){

                    final String text = search_list2.get(j).toLowerCase();
                    if(text.contains(charSequence)){
                        //filterList.add(search_list.get(j));
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(Search_View.this));
                mAdapter = new SearchAdapter(filterList,Search_View.this);
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    private void suggestionList() {

        ValueEventListener postListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("catSugList").getChildren()){

                    counter++;
                    Log.d("chair8",snapshot.getValue().toString());
                    search_list.add(snapshot.getValue().toString());//firebase data

                    long l = dataSnapshot.child("catSugList").getChildrenCount();
                    Log.d("chair81",""+l);

                    if(counter == dataSnapshot.child("catSugList").getChildrenCount())
                    {
                        for(int i =0 ; i < dataSnapshot.child("catSugList").getChildrenCount() ; i++) {

                            CatSuggested catsug = new CatSuggested(search_list.get(i));
                            catsug.save();
                            Log.d("fabulo",""+search_list.get(i));
                        }

                        ArrayList<CatSuggested> sugcat = (ArrayList<CatSuggested>) CatSuggested.listAll(CatSuggested.class);
                        Log.d("dhinchak",""+sugcat.size());

                        for(int i = 0 ; i < sugcat.size(); i++){

                            search_list2.add(sugcat.get(i).getCat_name().toString());
                            Log.d("dhinchak2",""+search_list2.get(i));

                        }
                        mAdapter = new SearchAdapter(search_list2, Search_View.this);
                        recyclerView.setAdapter(mAdapter);
                    }
                }
                Log.d("chair5",""+dataSnapshot.child("catSugList").getChildrenCount());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        searchlist.addValueEventListener(postListner);
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
                    Search_View.this,
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





