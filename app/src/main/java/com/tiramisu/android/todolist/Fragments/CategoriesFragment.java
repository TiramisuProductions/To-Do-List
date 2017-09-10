package com.tiramisu.android.todolist.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DatabaseReference;
import com.tiramisu.android.todolist.Adapter.CategoriesAdapter;
import com.tiramisu.android.todolist.Add_new_category;
import com.tiramisu.android.todolist.Model.Category;
import com.tiramisu.android.todolist.Model.WorldEvent;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static com.tiramisu.android.todolist.Model.StaticVar.categorylist;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    private Context mContext;
    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;

    // DatabaseReference databaseReference;
    private ArrayList<String> categoryNamelist = new ArrayList<String>();
    private ArrayList<String> categoryIdlist = new ArrayList<String>();
    private ArrayList<String> taskFolderIdlist = new ArrayList<String>();


    int CategoryCounter = 0;
    int no_of_categories = 0;
    int taskcounter = 0;
    private ArrayList<String> hello = new ArrayList<String>();

    DatabaseReference categoryreference,todoref;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        mContext = getContext();

        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_Categories);

        materialDesignFAM = (FloatingActionMenu)rootView.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton)rootView.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton)rootView.findViewById(R.id.material_design_floating_action_menu_item2);


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Button 1", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), Add_new_category.class));
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (getActivity(),Tasks.class);

                startActivity(intent);

                Toast.makeText(mContext, "Button 2", Toast.LENGTH_SHORT).show();
            }
        });


        /*String[] names={
                "Pranal",
                "Sid"   };
        */

        // Category.deleteAll(Category.class); //clear database

//        categoryreference = FirebaseDatabase.getInstance().getReference("Todo");
//        todoref = categoryreference.child("FOzpt21IejaDk20Sq8tBEO0bVVC3/Categories");

        EventBus.getDefault().register(this);
        categorylist= Category.listAll(Category.class);
        Log.d("size", String.valueOf(categorylist.size()));
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new CategoriesAdapter(mContext,categorylist);
        mRecyclerView.setAdapter(mAdapter);
        categoriesUpdate();






        return rootView;
    }
    @Subscribe
    public void onEvent(WorldEvent event){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                categoriesUpdate();

            }
        });




    }




    public void categoriesUpdate(){
        categorylist= Category.listAll(Category.class);
        mAdapter=new CategoriesAdapter(mContext,categorylist);
        mRecyclerView.setAdapter(mAdapter);


    }

}








