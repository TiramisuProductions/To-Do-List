package com.tiramisu.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.CategorySearchAdapter;
import com.tiramisu.android.todolist.Model.CategorySuggestionModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class AddNewCategory extends AppCompatActivity {

    private RecyclerView mRecyclerView_categorySerach;
    public  EditText search;
    private ImageView done;
    private List<CategorySuggestionModel> suggestionModelList =  new ArrayList<>();
    private ArrayList<CategorySuggestionModel> search_list2 = new ArrayList<>();
    private DatabaseReference CategoryRef;



    public CategorySearchAdapter mAdapter_categorySerach;

    DatabaseReference catSuggest;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);





        catSuggest = FirebaseDatabase.getInstance().getReference("category_suggetion_list");
        CategoryRef = FirebaseDatabase.getInstance().getReference("Todo/"+ StaticVar.UID + "/Categories");

        search = (EditText) findViewById( R.id.search_category);
        done = (ImageView)findViewById(R.id.donebtn_cat) ;
        mRecyclerView_categorySerach = (RecyclerView) findViewById(R.id.recyclerview_categorySearch);
        mRecyclerView_categorySerach.setHasFixedSize(true);
        mRecyclerView_categorySerach.setLayoutManager(new LinearLayoutManager(this));

        suggestionList();



        addTextListener();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = search.getText().toString().trim();


                String key_id1 = CategoryRef.push().getKey();




                CategoryRef.child(key_id1).child("Category_Name").setValue(category);




               finish();


            }
        });
    }

    private void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                charSequence = charSequence.toString().toLowerCase();

                final List<CategorySuggestionModel> filterList = new ArrayList<CategorySuggestionModel>();

                for(int j = 0; j < suggestionModelList.size(); j++){

                    final String text = suggestionModelList.get(j).getSuggestion().toLowerCase();
                    if(text.contains(charSequence)){
                        filterList.add(suggestionModelList.get(j));
                    }
                }
                mRecyclerView_categorySerach.setLayoutManager(new LinearLayoutManager(AddNewCategory.this));
                mAdapter_categorySerach = new CategorySearchAdapter(filterList,AddNewCategory.this);
                mRecyclerView_categorySerach.setAdapter(mAdapter_categorySerach);
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
                    //firebase data
                    suggestionModelList.add(new CategorySuggestionModel(snapshot.getValue().toString()));

                    long l = dataSnapshot.child("catSugList").getChildrenCount();
                    Log.d("chair81",""+l);

                    if(counter == dataSnapshot.child("catSugList").getChildrenCount())
                    {




                        mAdapter_categorySerach = new CategorySearchAdapter(suggestionModelList, AddNewCategory.this);
                        mRecyclerView_categorySerach.setAdapter(mAdapter_categorySerach);
                    }
                }
                Log.d("chair5",""+dataSnapshot.child("catSugList").getChildrenCount());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        catSuggest.addValueEventListener(postListner);
    }
}
