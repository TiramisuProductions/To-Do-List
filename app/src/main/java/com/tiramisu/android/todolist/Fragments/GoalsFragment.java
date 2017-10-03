package com.tiramisu.android.todolist.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.GoalsAdapter;
import com.tiramisu.android.todolist.Model.CategoryModel;
import com.tiramisu.android.todolist.Model.GoalsModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference toDoRef,goalsRef;
    private List<GoalsModel> goalsList=new ArrayList<>();


    public GoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_goals, container, false);

        mLayoutManager=new GridLayoutManager(getActivity().getApplicationContext(),2);

        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.goalsRecyclerView);
        mRecyclerView.setLayoutManager(mLayoutManager);


        toDoRef = FirebaseDatabase.getInstance().getReference("Todo");
        toDoRef.keepSynced(true);
        goalsRef = toDoRef.child(""+ StaticVar.UID+"/Goals");
        goalsRef.keepSynced(true);

        goalsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    GoalsModel goalsModel = new GoalsModel(snapshot.getKey(),snapshot.child("Goal_Name").getValue().toString());

                    GoalsAdapter goalsAdapter=new GoalsAdapter(getActivity(),goalsList);
                    mRecyclerView.setAdapter(mAdapter);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        // Inflate the layout for this fragment
        return rootView;
    }

}
