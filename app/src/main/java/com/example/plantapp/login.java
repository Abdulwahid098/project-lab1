package com.example.plantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
    EditText useremail, userpassword;
    CheckBox showlogin;
    Button ulogin;
    TextView forgot, newlogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ProgressDialog progressDialog;
    ImageView btngoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        useremail=findViewById(R.id.useremail);
        userpassword=findViewById(R.id.userpassword);
        ulogin = findViewById(R.id.ulogin); // Initialize the login button
        newlogin=findViewById(R.id.newlogin);
        forgot=findViewById(R.id.forgot);
        newlogin=findViewById(R.id.newlogin);
        forgot=findViewById(R.id.forgot);

        showlogin=findViewById(R.id.showlogin);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        FirebaseDatabase.getInstance();

        // Set the correct listener for button click
        ulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the login process

                // Perform login (You can show progress dialog here)
                Intent intent = new Intent(login.this, MainActivity.class); // Adjust your target activity
                startActivity(intent);
                finish();

            }
        });
        //// forget
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, forget.class); // Adjust your target activity
                startActivity(intent);

            }
        });
        ///New Account
        newlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, registration.class); // Adjust your target activity
                startActivity(intent);

            }
        });
        /// show password
        showlogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    /// show password
                    userpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    userpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        /// login Button
        ulogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkmethod();
            }
        });
    }
    private void checkmethod() {
        String email, password;
        email=useremail.getText().toString();
        password=userpassword.getText().toString();
        if (email.isEmpty())
        {
            useremail.setError("Please Enter Email Address");
            useremail.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            useremail.setError("Invalid Email Address");
            useremail.requestFocus();
            return;
        }
        else if (password.isEmpty())
        {
            userpassword.setError(" Please Enter Password");
            userpassword.requestFocus();
            return;
        }
        else if (password.length()<=6)
        {
            userpassword.setError("Enter 8 character Password");
            userpassword.requestFocus();
            return;
        }
        else
        {
            ProgressDialog progressDialog = new ProgressDialog(login.this);
            progressDialog.setTitle("Logging........");
            progressDialog.setMessage("Please wait while logging");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(login.this, "Logging Successful ", Toast.LENGTH_SHORT).show();
//                        emailverification();
                          Homepage();
                        finish();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        }

    }

    private void emailverification()
    {
        FirebaseUser muser=mAuth.getInstance().getCurrentUser();
        muser.isEmailVerified();
        Intent i=new Intent(login.this,login.class);
        startActivity(i);
        if(muser.isEmailVerified())
        {
            finish();
            Intent intent=new Intent(login.this,MainActivity.class);
            startActivity(intent);
        }
        else
        { Toast.makeText(login.this, "Logging.....", Toast.LENGTH_SHORT).show();
            //mAuth.signOut();

            Intent m=new Intent(login.this,MainActivity.class);
            startActivity(m);
        }
    }


    private void Homepage() {
        Intent i= new Intent(login.this,MainActivity.class);
        //   i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

}