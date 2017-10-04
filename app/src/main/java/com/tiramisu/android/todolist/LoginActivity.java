package com.tiramisu.android.todolist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.Introslider.Welcome;
import com.tiramisu.android.todolist.Model.UserUID;
import com.tiramisu.android.todolist.service.FirebaseBackgroundService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private SignInButton mGooglebtn;

    private static final  int RC_SIGN_IN=1;

    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth mAuth;

    private static final String TAG = "Login Activity";

    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button signInButton;

    private EditText emailId;

    private EditText passWord;

    private ProgressDialog progressDialog;

    private DatabaseReference UserRef;
    private int UserCounter;
    private  boolean userfound;
     @BindView(R.id.loginlayout) LinearLayout loginLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        emailId =(EditText) findViewById(R.id.email);
        passWord=(EditText) findViewById(R.id.password);

        FirebaseApp.initializeApp(this);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        UserRef.keepSynced(false);

        ButterKnife.bind(this);


        if (mAuth.getCurrentUser() !=null){

           // Intent n = new Intent(this,Home.class);
            //startActivity(n);

        }

        emailId=(EditText)findViewById(R.id.email);
        passWord=(EditText)findViewById(R.id.password);
        signInButton=(Button)findViewById(R.id.buttonSignup);

        progressDialog =new ProgressDialog(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() !=null){


                }
            }
        };

        mGooglebtn = (SignInButton) findViewById(R.id.googleBtn);
        // Google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this,"hey",Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mGooglebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable())
                {
                    signIn();
                }
                else
                {
                    Snackbar snack = Snackbar.make(loginLayout,"You are  not Connected to the Internet", Snackbar.LENGTH_LONG);
                    snack.show();
                }


            }
        });

//email registration
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogin();

            }
        });

    }

    private void userLogin() {
        String email =emailId.getText().toString().trim();
        String password=passWord.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Registering Please Wait....");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){





                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Registration error",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    ////////////////////// email registration end//////////////////////////////////




    private void  CheckIfUserExsist(final String email,final String uID,final String name)
    {
        userfound = false;

        FirebaseDatabase.getInstance().getReference().child("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            System.out.println("shammu " + dataSnapshot.getChildrenCount());
                            UserUID uid = snapshot.getValue(UserUID.class);
                            UserCounter++;
                            System.out.println("Pranal" + uid.getEmail() + email);
                            if (uid.getEmail().toString().trim().equals(email.toString().trim())) {
                                userfound = true;

                                Intent i = new Intent(LoginActivity.this,Home.class);
                                Log.d("MyUid",uid.getUID());
                                i.putExtra("uid",uid.getUID());

                                startActivity(i);

                                finish();
                            }

                            if (UserCounter == dataSnapshot.getChildrenCount()) {

                                if (userfound == true) {
                                    Toast.makeText(LoginActivity.this, "User Found", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "User Not Found", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(LoginActivity.this,Welcome.class);
                                    // Log.d("lolol",name);
                                    i.putExtra("name",name);
                                    i.putExtra("email",email);
                                    i.putExtra("uid",uID);
                                    startActivity(i);
                                }


                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }



    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                //google sign
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                //if login fails
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if(user.getDisplayName()==null)
                            {
                                Log.d("mouse","null null");
                            }
                            else {
                                Log.d("cat ","not null");
                            }
                            Log.d("rmail",user.getEmail());

                            CheckIfUserExsist(user.getEmail(),user.getUid(),user.getDisplayName());
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }






}
