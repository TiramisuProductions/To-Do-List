package com.tiramisu.android.todolist;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    @BindView(R.id.email_textInputForgot) TextInputLayout forgotPasswordEmailInput;
    @BindView(R.id.emailForgot) EditText forgotPasswordEditText;
    @BindView(R.id.reset_password) Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);
      mAuth=FirebaseAuth.getInstance();

      resetButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String email=forgotPasswordEditText.getText().toString();

              if (isValidEmail(email)){

                  mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful())
                         {
                             Toast.makeText(ResetPassword.this, "Check email to reset your password", Toast.LENGTH_SHORT).show();
                         }
                         else {
                             Toast.makeText(ResetPassword.this, "Failed to send reset password email", Toast.LENGTH_SHORT).show();
                         }
                      }
                  });
              }
              else {
                  forgotPasswordEmailInput.setError("Not valid email");
              }


          }
      });

    }
    //Email validation
    public final static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
