package com.tiramisu.android.todolist.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
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
    public GoalsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference toDoRef,goalsRef,goalsref2;
    private List<GoalsModel> goalsList=new ArrayList<>();
    int Counter;


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
        goalsRef = toDoRef.child(""+ StaticVar.UID);
        goalsref2 = goalsRef.child("Goals");
        goalsRef.keepSynced(true);
       // mRecyclerView.setAdapter(mAdapter);



        goalsref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Boom",""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        goalsref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //goalsList.add(new GoalsModel("goals","goals"));



              //  mRecyclerView.setAdapter(mAdapter);
                goalsList = new ArrayList<GoalsModel>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                 //   GoalsModel value = dataSnapshot1.getValue(GoalsModel.class);
                   // GoalsModel fire = new GoalsModel("AA","a");
                    //String name = value.getName();

                    Counter++;

                    Log.d("qwerty",""+dataSnapshot.getChildrenCount());
                    Log.d("hello","ello");

                    GoalsModel goal_name = new GoalsModel(dataSnapshot1.getKey(),dataSnapshot1.child("Goal_Name").getValue().toString());
                    goalsList.add(goal_name);

                    if(Counter==dataSnapshot.getChildrenCount())
                    {
                        mAdapter=new GoalsAdapter(getActivity(),goalsList);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                  //  fire.setName(name);



                    //goalsList.add(fire);

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
