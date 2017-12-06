package com.tiramisu.android.todolist.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiramisu.android.todolist.Adapter.CalendarAdapter;
import com.tiramisu.android.todolist.Adapter.CategoriesAdapter;
import com.tiramisu.android.todolist.AddNewCategory;
import com.tiramisu.android.todolist.AddNewTask;
import com.tiramisu.android.todolist.Home;
import com.tiramisu.android.todolist.Model.CategoryModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.TAGS;
import com.tiramisu.android.todolist.Tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {


    @BindView(R.id.categoryfragmentlayout)  RelativeLayout categoryFragmentRelativeLayout;
    @BindView(R.id.categoryrecylerview)  RecyclerView categoryRecylerView;
    @BindView(R.id.addcategoryfloatingaction) FloatingActionButton addCategoryFloatingActionButton;
    @BindView(R.id.addtaskfloatingaction) FloatingActionButton addTaskFloatingActionButton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView.LayoutManager layoutManager;
    private CategoriesAdapter adapter;
    private  List<CategoryModel >categoryList = new ArrayList<CategoryModel>();
    private FirebaseAuth firebaseAuth;
    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.bind(this,rootView);
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        categoryRecylerView.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

addListener();





        //Add category button
       addCategoryFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), AddNewCategory.class));
            }
        });

        //Add task button
        addTaskFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (getActivity(),AddNewTask.class);
                intent.putExtra("alltasks",true);
                startActivity(intent);
            }
        });










        return rootView;
    }


public void addListener(){
    db.collection(TAGS.TODO).document(firebaseAuth.getUid()).collection(TAGS.CATEGORIES).addSnapshotListener(new EventListener<QuerySnapshot>() {

        @Override
        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
            categoryList.clear();
            for(DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()){
                CategoryModel categoryModel = documentSnapshot.toObject(CategoryModel.class);

                categoryModel.setCategoryId(documentSnapshot.getId());
              //  Log.d("qwerty", categoryModel.getCategoryName() + categoryModel.getCategoryId());
                categoryList.add(categoryModel);
            }

            adapter = new CategoriesAdapter(getActivity(), categoryList);
            categoryRecylerView.setAdapter(adapter);
        }
    });
}


    }















