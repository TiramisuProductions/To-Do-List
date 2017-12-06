package com.tiramisu.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiramisu.android.todolist.Model.CategoryModel;
import com.tiramisu.android.todolist.Model.Details;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.Model.UserModel;
import com.tiramisu.android.todolist.Model.UserUID;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;



@RequiresApi(api = Build.VERSION_CODES.N)
public class NewUserForm extends AppCompatActivity {

    //@BindView(R.id.dob) EditText dob;
    @BindView(R.id.name) EditText nameEditText;
    @BindView(R.id.username) EditText userNameEditText;
    @BindView(R.id.genderradiogroup) RadioGroup genderGroup;
    @BindView(R.id.male) RadioButton maleRadio;
    @BindView(R.id.female) RadioButton femaleRadio;
    @BindView(R.id.other) RadioButton otherRadio;
    @BindView(R.id.done) ImageView doneImageView;

    @BindView(R.id.newuserformlayout) RelativeLayout newUserFormLayout;
    @BindView(R.id.usernameprogressbar) ProgressBar userNameProgressBar;
    @BindView(R.id.usernameavailable) TextView userNameAvaiable;
    @BindView(R.id.usernamenotavailable) TextView userNameNotAvailable;
    String name,uid,email,username,gender;

    private boolean userExist = false,dataArrived = false;


    //private final   Calendar myCalendar = Calendar.getInstance();
    private  Intent i;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedpreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuserform);
        ButterKnife.bind(this);
        i = getIntent();
        checkIfNull();
        nameEditText.setText(name);
        sharedpreferences =  getSharedPreferences(TAGS.SHAREDPREF, Context.MODE_PRIVATE);



        doneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });

        userNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(editable.toString(),"dad");
                if(editable.toString().contains(" "))
                {
                    userNameEditText.setError("No Spaces Allowed");
                    userNameAvaiable.setVisibility(View.INVISIBLE);
                    userNameNotAvailable.setVisibility(View.INVISIBLE);
                    userNameProgressBar.setVisibility(View.INVISIBLE);

                }
                else{
                    userNameAvaiable.setVisibility(View.INVISIBLE);
                    userNameNotAvailable.setVisibility(View.INVISIBLE);
                    userNameProgressBar.setVisibility(View.VISIBLE);
                    Query capitalCities = db.collection("Users").whereEqualTo("userName", editable.toString());
                    capitalCities.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            QuerySnapshot document = task.getResult();
                            Log.d("test",""+document.size());
                            if(document.size()>0){
                                userExist = true;
                                dataArrived = true;
                                userNameProgressBar.setVisibility(View.INVISIBLE);
                                userNameAvaiable.setVisibility(View.INVISIBLE);
                                userNameNotAvailable.setVisibility(View.VISIBLE);

                            }
                            else {
                                userExist = false;
                                dataArrived = true;
                                userNameProgressBar.setVisibility(View.INVISIBLE);
                                userNameNotAvailable.setVisibility(View.INVISIBLE);
                                userNameAvaiable.setVisibility(View.VISIBLE);
                            }



                        }
                    });
                }


            }
        });



    }


    private void checkIfNull () //checking if any User Auth values are null
    {
        if(i.getStringExtra("email")==null)
        {
            email = " ";
        }
        else{
            email = i.getStringExtra("email");
         }
        if(i.getStringExtra("name")==null)
        {
            name = " ";
        }
        else{
            name = i.getStringExtra("name");
        }
        if(i.getStringExtra("uid")==null)
        {
            uid = " ";
        }
        else{
            uid = i.getStringExtra("uid");
        }
    }


    private void userRegister(){

        //Values into Variables
        int checkedRadioButtonId = genderGroup.getCheckedRadioButtonId();
        if(checkedRadioButtonId == R.id.male)
        {
        gender = "M";
        }
        else if (checkedRadioButtonId == R.id.female){
            gender ="F";
        }
        else{
            gender = "O";
        }
        username = userNameEditText.getText().toString();


        //Validation
        if(username.trim().equals(""))
        {
            userNameEditText.setError("Enter A Username ");
        }
        else if (genderGroup.getCheckedRadioButtonId() == -1)
        {
            showSnackbar("Select A Gender");
        }else if(nameEditText.equals("")){
            nameEditText.setError("Enter A Name");
        }
        else if(dataArrived&&userExist){
            userNameEditText.setError("Username Exist");
        }
            else if(dataArrived && !userExist){
            UserModel userData = new UserModel(email,name,uid,username,gender);
            db.collection("Users").document(email).set(userData);
            initializeStructure();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(TAGS.SHAREDPREFUID,uid);
            editor.commit();
            startActivity(new Intent(NewUserForm.this,Home.class));
            finish();
        }
        else {
                showSnackbar("Please Connect To Internet To Validate Username");
        }

    }


    private void initializeStructure(){

        CategoryModel category = new CategoryModel ("Chores");
        db.collection(TAGS.TODO).document(uid).collection(TAGS.CATEGORIES).add(category).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("NewUserForm", "DocumentSnapshot added with ID: " + documentReference.getId());
               // init1.put("taskName","Code");
              //  init1.put("taskDueDate","1201002");
              //  db.collection("Todo").document(uid).collection("categories").document(documentReference.getId()).collection("tasks").add(init1);
            }
        });



    }

    private void showSnackbar(String message){
        Snackbar snackbar = Snackbar.make(newUserFormLayout,message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }





















}
