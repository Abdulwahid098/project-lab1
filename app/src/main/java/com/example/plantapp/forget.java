package com.example.plantapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plantapp.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forget extends AppCompatActivity {
    CheckBox passcheck;
    EditText femail;
    Button resetpassword;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
//        passcheck=findViewById(R.id.pascheck);
        femail=findViewById(R.id.femail);
        resetpassword=findViewById(R.id.resetpassword);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                email = femail.getText().toString();
                if (email.isEmpty()) {
                    femail.setError("Please enter email address");
                    femail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    femail.setError("Invalid email address");
                    femail.requestFocus();
                    return;
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(forget.this, " Reset Password email Send ", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(forget.this, login.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(forget.this, "password not reset", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }


}