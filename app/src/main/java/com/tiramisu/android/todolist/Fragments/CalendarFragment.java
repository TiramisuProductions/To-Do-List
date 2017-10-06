package com.tiramisu.android.todolist.Fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;


import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CalendarFragment extends Fragment {


    private DatabaseReference calref;
    int counter = 0, counter2 = 0, counter_tasks = 0;
    private ArrayList<String> dueDateList1 = new ArrayList<String>();
    private ArrayList<String> tasksOnClickedDate_List = new ArrayList<String>();

    String date = "";
    Date date2;
    String dateclicked = "";

    SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");


    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calender, view, false);
        calref = FirebaseDatabase.getInstance().getReference("Todo/"+ StaticVar.UID +"/Categories");


        dueDateList1.clear();
        calref.keepSynced(true);

///////////////////////////////Calendar View part////////////////////////////////////
        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.calender1, caldroidFragment);
        t.commit();
////////////////////////////////////////////////////////////////////////////////////

        calref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final int counter_categories = (int) dataSnapshot.getChildrenCount();
                Log.d("cremeux4", "" + counter_categories);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    counter++;
                    Log.d("cremeux5", "" + counter);

                    Log.d("cremeux1", "" + snapshot.getValue());
                    Log.d("cremeux3", "" + snapshot.getChildrenCount());

                    if (snapshot.child("Tasks") != null) {
                        snapshot.child("Tasks").getRef().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Log.d("cremCount", ""+snapshot.child("Tasks").getChildrenCount());

                                for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {

                                    Log.d("cremeux2", "" + snapshot2.child("dueDate").getValue().toString());

                                    dueDateList1.add("" + snapshot2.child("dueDate").getValue().toString());

                                }
                                if (counter == counter_categories) {
                                    Log.d("DueSize", "" + dueDateList1.size());


                                    for(int i =0; i < dueDateList1.size(); i++){

                                        date = getDate(Long.valueOf(dueDateList1.get(i)), "dd/MM/yyyy");
                                        try {
                                            date2 = myFormat.parse(date);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        caldroidFragment.setBackgroundDrawableForDate(new ColorDrawable(Color.parseColor("#ff4000")),date2);

                                        //Log.d("date34", ""+date);
                                        //Log.d("temp4", ""+dueDateList1.get(i));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //On calendar date click listener
        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {


                Toast.makeText(getContext(), myFormat.format(date),Toast.LENGTH_SHORT).show();

                dateclicked = myFormat.format(date);

                calref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){


                            Log.d("shower1", ""+snapshot.getValue());
                            Log.d("shower2", ""+snapshot.getKey());

                            if (snapshot.child("Tasks") != null) {


                                snapshot.child("Tasks").getRef().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for (DataSnapshot snapshot2:dataSnapshot.getChildren()){

                                            String dateTemp = getDate(Long.valueOf(snapshot2.child("dueDate").getValue().toString()), "dd/MM/yyyy");


                                            if(dateclicked.trim().equals( dateTemp.trim())){ //checks date clicked with duedate

                                                Toast.makeText(getContext(),"inside if",Toast.LENGTH_SHORT).show();

                                                Log.d("airteldth", "" + snapshot2.child("taskName").getValue().toString());
                                                tasksOnClickedDate_List.add(""+snapshot2.child("taskName").getValue().toString()); //lists all tasks on
                                                                                                                                   //clicked date
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

       // Toast.makeText(getContext(),""+tasksOnClickedDate_List.get(0),Toast.LENGTH_SHORT).show();



        return rootView;
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
