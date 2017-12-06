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

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment {

    @BindView(R.id.goalsRecyclerView) RecyclerView goalRecylerView;
    public GoalsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<GoalsModel> goalsList=new ArrayList<>();


    public GoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_goals, container, false);

        mLayoutManager=new GridLayoutManager(getActivity().getApplicationContext(),2);

        goalRecylerView=(RecyclerView)rootView.findViewById(R.id.goalsRecyclerView);
        goalRecylerView.setLayoutManager(mLayoutManager);



        // Inflate the layout for this fragment
        return rootView;
    }

}
