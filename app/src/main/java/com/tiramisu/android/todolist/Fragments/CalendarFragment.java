package com.tiramisu.android.todolist.Fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import com.tiramisu.android.todolist.Adapter.CalendarAdapter;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.R;


import java.util.ArrayList;
import java.util.Calendar;



public class CalendarFragment extends Fragment {


    private DatabaseReference calref;
    int counter = 0;
    private ArrayList<String> dueDateList1 = new ArrayList<String>();
    public RecyclerView mRecyclerView;
    public CalendarAdapter madapter;


    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calender, view, false);
        calref = FirebaseDatabase.getInstance().getReference("Todo/"+ StaticVar.UID +"/Categories");


        dueDateList1.clear();
        calref.keepSynced(true);

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

                                    madapter = new CalendarAdapter(dueDateList1,getContext());
                                    mRecyclerView.setAdapter(madapter);
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


        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.calender1, caldroidFragment);
        t.commit();


        return rootView;
    }
}
