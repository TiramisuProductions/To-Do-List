package com.tiramisu.android.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Model.Details;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.Model.UserUID;

import butterknife.BindView;
import butterknife.ButterKnife;



@RequiresApi(api = Build.VERSION_CODES.N)
public class NewUserForm extends AppCompatActivity {

    //@BindView(R.id.dob) EditText dob;
    @BindView(R.id.name) EditText name;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.genderradiogroup) RadioGroup GenderGroup;
    @BindView(R.id.male) RadioButton maleradio;
    @BindView(R.id.female) RadioButton femaleradio;
    @BindView(R.id.other) RadioButton otherradio;
    @BindView(R.id.done) ImageView done;
    @BindView(R.id.progressbar) ProgressBar progressBar;

    private DatabaseReference UserRef;
    private DatabaseReference refFirst;
    private boolean userfound;
    //String d_o_b;
    String genderselected;
    private int UserCounter;
    private boolean searchfinished;

    //private final   Calendar myCalendar = Calendar.getInstance();
    private  Intent i;

    String Category_Name1 = "Personal";
    String Category_Name2 = "Work";
    String Goal_Name1 ="Exercise",Goal_Name2="Build a Skill",Goal_Name3 = "Self Time ";
    String date1 = "13-10-1996",name1 = "code my project",status1 = "not done",time1 = "12:00";
    String date2 = "13-10-1996",name2 = "code my project",status2 = "not done",time2 = "12:00";
    String datez = "13-10-2000",namez = "code his project",statusz = "done",timez = "13:00";
    SharedPreferences.Editor editor;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuserform);
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        i = getIntent();

        Toast.makeText(NewUserForm.this, i.getStringExtra("name") + i.getStringExtra("email") + i.getStringExtra("uid"), Toast.LENGTH_LONG).show();
        name.setText(i.getStringExtra("name"));

        maleradio.setSelected(true);

        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        refFirst = FirebaseDatabase.getInstance().getReference();

        if (i.getStringExtra("name") != null)

        {
            username.setText(i.getStringExtra("name").toLowerCase().replaceAll("\\s",""));
        }



        //updateLabel();

        usernamevalidation();

        /*final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //updateLabel();
            }

        };*/

        //dob.requestFocus();

        GenderGroup.check(R.id.male);

        /*dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NewUserForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                usernamevalidation();




                Log.d("textchange",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                if(validation())
                {
                    initialisation(i.getStringExtra("email"),i.getStringExtra("uid"));
                }




            }
        });




        GenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                if (null != rb && i > -1) {
                    genderselected = ""+rb.getText();
                    Toast.makeText(NewUserForm.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /*@RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        //d_o_b = sdf.format(myCalendar.getTime());//////////////////////
        //dob.setText(sdf.format(myCalendar.getTime()));
    }*/




    public void initialisation(String email,String Uid){

        UserUID user = new UserUID(email, Uid,username.getText().toString().toLowerCase().replaceAll("\\s",""));
        String id =  UserRef.push().getKey();
        UserRef.child(id).setValue(user);

        createFirstStructure();


        Toast.makeText(NewUserForm.this,"User Registered",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
    }



    public boolean validation()
    {
        if(name.getText().toString().equals(""))
        {
            name.setError("Please Enter Name");
            return  false;
        }

        if(username.getText().toString().equals(""))
        {
            username.setError("Please Enter Username");

            return  false;
        }

        if(userfound==true)
        {
            return false;
        }



        return  true;

    }






    public void usernamevalidation()
    {

        progressBar.setVisibility(View.VISIBLE);
        userfound = false;


        FirebaseDatabase.getInstance().getReference().child("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            System.out.println("shammu " + dataSnapshot.getChildrenCount());
                            UserUID uid = snapshot.getValue(UserUID.class);
                            UserCounter++;
                            if (uid.getUsername().toString().trim().equals(username.getText().toString().trim().toLowerCase().replaceAll("\\s",""))) {
                                userfound = true;
                                Toast.makeText(NewUserForm.this, "User Found", Toast.LENGTH_LONG).show();
                                username.setError("Username Exist ");
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            if (UserCounter == dataSnapshot.getChildrenCount()) {

                                if (userfound == true) {
                                    username.setError("Username Exsist ");
                                } else {


                                }


                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });





    }



    /*public boolean validatedate()
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            String date = ((EditText) findViewById(R.id.dob)).getText().toString(); // EditText to check
            java.util.Date parsedDate = dateFormat.parse(date);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            // If the string can be parsed in date, it matches the SimpleDateFormat
            // Do whatever you want to do if String matches SimpleDateFormat.

            Toast.makeText(NewUserForm.this,"It is A Date",Toast.LENGTH_LONG).show();
            return  true;
        }
        catch (java.text.ParseException e) {
            // Else if there's an exception, it doesn't
            // Do whatever you want to do if it doesn't.
            Toast.makeText(NewUserForm.this,"It is Not A Date",Toast.LENGTH_LONG).show();
            return false;


        }
    }*/

    private void createFirstStructure(){

        String Uid = i.getStringExtra("uid");

        String key_id1 = refFirst.push().getKey();
        String key_id2 = refFirst.push().getKey();
        String key_id3 = refFirst.push().getKey();
        String key_id4 = refFirst.push().getKey();
        String key_id5 = refFirst.push().getKey();
        String key_id6 = refFirst.push().getKey();
        String key_id7 = refFirst.push().getKey();

        Details details = new Details(key_id1,name.getText().toString(),username.getText().toString(),genderselected);

        refFirst.child("Todo").child(Uid).child("Details").child(key_id1).setValue(details);
        long millis = System.currentTimeMillis();
        Log.d("lastupdated",String.valueOf(millis));

        //category 1,task1
        refFirst.child("Todo").child(Uid).child("Categories").child(key_id2).child("Category_Name").setValue(Category_Name1);
        //task2
        //category 2,task1
        refFirst.child("Todo").child(Uid).child("Categories").child(key_id4).child("Category_Name").setValue(Category_Name2);

        //Goals
        refFirst.child("Todo").child(Uid).child("Goals").child(key_id3).child("Goal_Name").setValue(Goal_Name1);
        refFirst.child("Todo").child(Uid).child("Goals").child(key_id5).child("Goal_Name").setValue(Goal_Name2);
        refFirst.child("Todo").child(Uid).child("Goals").child(key_id6).child("Goal_Name").setValue(Goal_Name3);





    }
}
