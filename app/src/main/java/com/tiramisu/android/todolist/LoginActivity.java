package com.tiramisu.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.TextView;
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

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiramisu.android.todolist.Introslider.Welcome;


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
    private FirebaseAuth firebaseAuth;
    @BindView(R.id.link_signup) TextView signup;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
     @BindView(R.id.loginlayout) LinearLayout loginLayout;
     @BindView(R.id.email) EditText emailEditText;
     @BindView(R.id.password) EditText passwordEditText;
     SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getUid()!=null){
            startActivity(new Intent(LoginActivity.this,Home.class));
            finish();
        }
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);



        sharedPreferences =  getSharedPreferences(TAGS.SHAREDPREF, Context.MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();


        ButterKnife.bind(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });




        //emailId=(EditText)findViewById(R.id.email);
        //password=(EditText)findViewById(R.id.password);
        signInButton=(Button)findViewById(R.id.buttonSignup);



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

    private void showSnackbar(String message){
        Snackbar snackbar = Snackbar.make(loginLayout,message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void userLogin() {
       final String email =emailEditText.getText().toString().trim();
        String password=passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            emailEditText.setError("Please Enter Email ");
            return;
        }

        if(TextUtils.isEmpty(password)){
            passwordEditText.setError("Please Enter Password");
            return;
        }




         firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){
                            //start the profile activity

                            Intent i = new Intent(LoginActivity.this,Home.class);

                            i.putExtra("uid",firebaseAuth.getUid());
                            startActivity(i);



                        }
                        else {
                            showSnackbar("Wrong Email or Password");
                        }
                    }
                });
    }









    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
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
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            final FirebaseUser user = firebaseAuth.getCurrentUser();


                            Query capitalCities = db.collection("Users").whereEqualTo("email", user.getEmail());
                            capitalCities.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    QuerySnapshot document = task.getResult();
                                    Log.d("test",""+document.size());
                                    if(document.size()>0){
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(TAGS.SHAREDPREFUID,firebaseAuth.getUid());
                                        editor.commit();
                                        startActivity(new Intent(LoginActivity.this,Home.class));

                                    }
                                    else {
                                        Intent i = new Intent(LoginActivity.this,Welcome.class);
                                        i.putExtra("email",user.getEmail());
                                        i.putExtra("name",user.getDisplayName());
                                        i.putExtra("uid",user.getUid());
                                        startActivity(i);
                                    }



                                }
                            });





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

    private void checkIfGoogleUserExist(){

    }



    public void showToast(String message){
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }






}
