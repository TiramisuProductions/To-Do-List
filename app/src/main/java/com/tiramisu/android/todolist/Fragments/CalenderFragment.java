package com.tiramisu.android.todolist.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;
import com.stacktips.view.utils.CalendarUtils;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment {

    com.imanoweb.calendarview.CustomCalendarView calendarView;
    private DatabaseReference calref;

    public CalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calender, view, false);
        calref = FirebaseDatabase.getInstance().getReference("Todo/"+ StaticVar.UID +"/Categories");

        calendarView = (CustomCalendarView)rootView.findViewById(R.id.calendar_view);
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        calref.keepSynced(true);

        calref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot snapshot : dataSnapshot.getChildren()){



                    Log.d("cremeux1", ""+snapshot.getValue());

                    if(snapshot.child("Tasks")!= null)
                    {
                        snapshot.child("Tasks").getRef().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot2 : dataSnapshot.getChildren()){

                                    Log.d("cremeux2", ""+snapshot2.child("dueDate").getValue().toString());

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

        List decorators = new ArrayList<>();
        decorators.add(new ColorDecorator() );
        calendarView.setDecorators(decorators);
        calendarView.(currentCalendar);

        return rootView;
    }

    private class ColorDecorator implements DayDecorator {
        @Override
        public void decorate(DayView dayView) {
            if (CalendarUtils.isToday(Calendar.getInstance())) {
                int color = Color.parseColor("#a9afb9");
                dayView.setBackgroundColor(color);
            }
        }
    }

}
