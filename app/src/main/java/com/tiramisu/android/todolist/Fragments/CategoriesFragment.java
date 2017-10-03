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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.CategoriesAdapter;
import com.tiramisu.android.todolist.AddNewCategory;
import com.tiramisu.android.todolist.AddNewTask;
import com.tiramisu.android.todolist.Model.CategoryModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {

    private Context mContext;
    private RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private DatabaseReference toDoRef,categoryRef;
    private FloatingActionMenu materialDesignFAM;
    public RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatingActionButton1, floatingActionButton2;
    public List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
    int CategoryCounter = 0;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        mContext = getContext();
        mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);

        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_Categories);
        mRecyclerView.setLayoutManager(mLayoutManager);
        materialDesignFAM = (FloatingActionMenu)rootView.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton)rootView.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton)rootView.findViewById(R.id.material_design_floating_action_menu_item2);

        //Add category button
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), AddNewCategory.class));
            }
        });

        //Add task button
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (getActivity(),AddNewTask.class);
                intent.putExtra("alltasks",true);
                startActivity(intent);
            }
        });

        toDoRef = FirebaseDatabase.getInstance().getReference("Todo");
        toDoRef.keepSynced(true);
        categoryRef = toDoRef.child(""+ StaticVar.UID+"/Categories");
        categoryRef.keepSynced(true);


        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryModelList.clear();
                categoryModelList.add(new CategoryModel("ALL","All"));
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    CategoryCounter++;
                    CategoryModel categoryModel = new CategoryModel(snapshot.getKey(),snapshot.child("Category_Name").getValue().toString());
                    if(!categoryModel.getName().equals("All"))
                    {
                        categoryModelList.add(categoryModel);
                    }

                    if(CategoryCounter==dataSnapshot.getChildrenCount())
                    {
                        CategoryCounter=0;
                        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity(),categoryModelList);
                        mRecyclerView.setAdapter(categoriesAdapter);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }





    }















