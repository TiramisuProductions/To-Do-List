package com.tiramisu.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tiramisu.android.todolist.Introslider.Welcome;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sarvesh Palav on 12/1/2017.
 */

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @BindView(R.id.email_textInput) TextInputLayout emailInputLayout;
    @BindView(R.id.password_textInput) TextInputLayout passwordInputLayout;
    @BindView(R.id.email) EditText emailEditText;
    @BindView(R.id.password) EditText passwordEditText;
    @BindView(R.id.signUp) Button signUpButton;
    @BindView(R.id.signUp_layout) ConstraintLayout signUpLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void showSnackbar(String message){
        Snackbar snackbar = Snackbar.make(signUpLayout,message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email = emailEditText.getText().toString().trim();
        String password  = passwordEditText.getText().toString().trim();

        //checking if email and passwords are empty //validation
        if(isValidEmail(email)){
            if(TextUtils.isEmpty(password)){
                passwordInputLayout.setError("Please Enter Password");
                return;
            }
            if(passwordEditText.getText().toString().length()<8){
                passwordInputLayout.setError("Password Should be at least 8 characters");
                return;
            }
            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){

                                // startActivity(new Intent(getApplicationContext(), Welcome.class));
                                Log.d("ui",firebaseAuth.getUid());
                                Intent i = new Intent(SignUpActivity.this,Welcome.class);
                                i.putExtra("email",email);
                                i.putExtra("name","");
                                i.putExtra("uid",firebaseAuth.getUid());
                                startActivity(i);
                            }else{
                                //display some message here
                                showSnackbar("Email  Already Exist");
                            }

                        }
                    });
            return;
        }
        else{
            emailInputLayout.setError("Not valid");
            return;
        }

    }

    //Email validation
    public final static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
