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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registration extends AppCompatActivity {
    CheckBox showlogin;
    EditText username, useremail, userpassword,userno;
    Button btnregister;
    TextView already;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    DatabaseReference mReference;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        showlogin=findViewById(R.id.showlogin);
        username=findViewById(R.id.username);
        userpassword=findViewById(R.id.userpassword);
        useremail=findViewById(R.id.useremail);
        userno=findViewById(R.id.userno);
        already=findViewById(R.id.already);
        btnregister=findViewById(R.id.btnregister);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        /// Register Button
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createaccount();

            }
        });;
        //// Already account
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(registration.this,login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        //// Password show
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

    }
    private void createaccount() {
        String name, email, password,number;
        name=username.getText().toString();
        email=useremail.getText().toString();
        password=userpassword.getText().toString();
        number=userno.getText().toString();

        if(name.isEmpty())
        {
            username.setError("Enter Username");
            username.requestFocus();
            return;
        }
        else if (email.isEmpty())
        {
            useremail.setError("Enter Email Address");
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
            userpassword.setError("Enter Password");
            userpassword.requestFocus();
            return;
        }
        else if (password.length()<=6)
        {
            userpassword.setError("Enter 8 character Password");
            userpassword.requestFocus();
            return;
        }
        else if (number.isEmpty())
        {
            userno.setError("Enter Mobile Number");
            userno.requestFocus();
            return;
        }
        else if(!numberchecker(number))
        {
            userno.setError("Invalid Mobile No");
            userno.requestFocus();
            return;
        }
        else
        {   ProgressDialog progressDialog = new ProgressDialog(registration.this);
            progressDialog.setMessage("Creating Account .....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            //// Registration Code

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        mUser=mAuth.getCurrentUser();
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                        progressDialog.dismiss();
                        Toast.makeText(registration.this, "Registration is Successful ", Toast.LENGTH_SHORT).show();
//                        Sendverification();
                        openMain();

                        finish();
                    }
                    else
                    {
                        Toast.makeText(registration.this, "Registration is Unsuccessful", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }


    }

    private void Sendverification()
    {
        FirebaseUser mUser=mAuth.getCurrentUser();
        mUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(registration.this, "Verification Email send", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    finish();

                }
                else
                {
                    Toast.makeText(registration.this, "Error Occure", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openMain()
    {
        Intent i =new Intent(registration.this, login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    private boolean numberchecker(String userno1)
    {
        Pattern p=Pattern.compile("[0-9]{11}");
        Matcher m=p.matcher(userno1);
        return m.matches();
    }

}
