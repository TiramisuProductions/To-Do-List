package com.tiramisu.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiramisu.android.todolist.Adapter.CategoriesAdapter;
import com.tiramisu.android.todolist.Adapter.CategorySearchAdapter;
import com.tiramisu.android.todolist.Model.CategoryModel;
import com.tiramisu.android.todolist.Model.CategorySuggestionModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewCategory extends AppCompatActivity {

    @BindView(R.id.categorysearch) RecyclerView categorySearchRecylerview;
    @BindView(R.id.searchcategory)  EditText searchEditText;
    @BindView(R.id.done) ImageView done;
    private List<CategoryModel> suggestionList =  new ArrayList<>();
    public CategorySearchAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        categorySearchRecylerview.setHasFixedSize(true);
        categorySearchRecylerview.setLayoutManager(new LinearLayoutManager(this));
        suggestionList();
        addTextListener();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(searchEditText.getText().toString().trim())){
                    searchEditText.setError("Search Cannot be Empty");
                    return;
                }

                CategoryModel category = new CategoryModel (searchEditText.getText().toString());
                db.collection(TAGS.TODO).document(firebaseAuth.getUid()).collection(TAGS.CATEGORIES).add(category).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent i = new Intent(AddNewCategory.this,Home.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    }
                });


            }
        });
    }

    private void addTextListener() {

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                charSequence = charSequence.toString().toLowerCase();

                final List<CategoryModel> filterList = new ArrayList<CategoryModel>();

                for(int j = 0; j < suggestionList.size(); j++){

                    final String text = suggestionList.get(j).getCategoryName().toLowerCase();
                    if(text.contains(charSequence)){
                        filterList.add(suggestionList.get(j));
                    }
                }
                categorySearchRecylerview.setLayoutManager(new LinearLayoutManager(AddNewCategory.this));
                adapter = new CategorySearchAdapter(filterList,AddNewCategory.this);
                categorySearchRecylerview.setAdapter(adapter);
            }
        });
    }

    private void suggestionList() {

        db.collection(TAGS.CATEGORYSUGGESTION).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for(DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()){
                    CategoryModel categoryModel = documentSnapshot.toObject(CategoryModel.class);

                    categoryModel.setCategoryId(documentSnapshot.getId());
                    Log.d("qwerty", categoryModel.getCategoryName() + categoryModel.getCategoryId());
                    suggestionList.add(categoryModel);
                }
                adapter = new CategorySearchAdapter(suggestionList,AddNewCategory.this);
                categorySearchRecylerview.setAdapter(adapter);

            }
        });


    }
}
