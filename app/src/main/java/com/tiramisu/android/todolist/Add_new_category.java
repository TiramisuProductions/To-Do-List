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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Adapter.CategorySearchAdapter;
import com.tiramisu.android.todolist.Model.CatSuggested;
import com.tiramisu.android.todolist.Model.Category;
import com.tiramisu.android.todolist.Model.Task;
import com.tiramisu.android.todolist.Model.WorldEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class Add_new_category extends AppCompatActivity {

    private RecyclerView mRecyclerView_categorySerach;
    public  EditText search;
    private Button done;
    private ArrayList<String> search_list = new ArrayList<String>();
    private ArrayList<String> search_list2 = new ArrayList<String>();

    public CategorySearchAdapter mAdapter_categorySerach;

    DatabaseReference catSuggest,cat_to_dbref;
    int counter = 0;
    String uid = "FOzpt21IejaDk20Sq8tBEO0bVVC3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);

        CatSuggested.deleteAll(CatSuggested.class);

        catSuggest = FirebaseDatabase.getInstance().getReference("category_suggetion_list");
        cat_to_dbref = FirebaseDatabase.getInstance().getReference("Todo/"+ uid + "/Categories");

        //final String key_new_cat = cat_to_dbref.push().getKey();

        search = (EditText) findViewById( R.id.search_category);
        done = (Button)findViewById(R.id.donebtn_cat) ;
        mRecyclerView_categorySerach = (RecyclerView) findViewById(R.id.recyclerview_categorySearch);
        mRecyclerView_categorySerach.setHasFixedSize(true);
        mRecyclerView_categorySerach.setLayoutManager(new LinearLayoutManager(this));

        suggestionList();

        // mAdapter_categorySerach = new CategorySearchAdapter(search_list,this);
        //  mRecyclerView_categorySerach.setAdapter(mAdapter_categorySerach);

        addTextListener();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = search.getText().toString().trim();

                Category category1 = new Category (category, (long) 0);
                category1.save();



                /*CategoriesFragment categoriesFragment = new CategoriesFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content,categoriesFragment,categoriesFragment.getTag()).commit();*/
                Toast.makeText(Add_new_category.this, "New Category " + category1, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new WorldEvent("Hello EventBus!"));
                Intent i = new Intent(Add_new_category.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
               /* if( search.getText().toString().length() == 0 )
                    search.setError( "Enter category name!" );
                Toast.makeText(Add_new_category.this, "New Category " + search.getText().toString() + "added", Toast.LENGTH_SHORT).show();
*/
                //cat_to_dbref.child(key_new_cat).child("Category Name").setValue(search.getText().toString());

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

                final ArrayList<String> filterList = new ArrayList<String>();

                for(int j = 0; j < search_list2.size(); j++){

                    final String text = search_list2.get(j).toLowerCase();
                    if(text.contains(charSequence)){
                        //filterList.add(search_list.get(j));
                    }
                }
                mRecyclerView_categorySerach.setLayoutManager(new LinearLayoutManager(Add_new_category.this));
                mAdapter_categorySerach = new CategorySearchAdapter(filterList,Add_new_category.this);
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
                    search_list.add(snapshot.getValue().toString());//firebase data

                    long l = dataSnapshot.child("catSugList").getChildrenCount();
                    Log.d("chair81",""+l);

                    if(counter == dataSnapshot.child("catSugList").getChildrenCount())
                    {
                        for(int i =0 ; i < dataSnapshot.child("catSugList").getChildrenCount() ; i++) {

                            CatSuggested catsug = new CatSuggested(search_list.get(i));
                            catsug.save();
                            Log.d("fabulo",""+search_list.get(i));
                        }

                        ArrayList<CatSuggested> sugcat = (ArrayList<CatSuggested>) CatSuggested.listAll(CatSuggested.class);
                        Log.d("dhinchak",""+sugcat.size());

                        for(int i = 0 ; i < sugcat.size(); i++){

                            search_list2.add(sugcat.get(i).getCat_name().toString());
                            Log.d("dhinchak2",""+search_list2.get(i));

                        }
                        mAdapter_categorySerach = new CategorySearchAdapter(search_list2, Add_new_category.this);
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
