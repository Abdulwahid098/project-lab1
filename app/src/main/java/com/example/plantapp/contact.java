package com.example.plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class contact extends AppCompatActivity {
    EditText c_email, c_subject,message;
    Button send;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        c_email=findViewById(R.id.c_email);
        c_subject=findViewById(R.id.subject);
        message=findViewById(R.id.message);
        send=findViewById(R.id.send);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email1,subject1,message1;
                email1=c_email.getText().toString().trim();
                subject1=c_subject.getText().toString().trim();
                message1=message.getText().toString().trim();

                if (email1.isEmpty())
                {
                    c_email.setError("Enter Email Address");
                    c_subject.requestFocus();
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
                {
                    c_email.setError("Invalid Email Address");
                    c_subject.requestFocus();
                    return;
                }
                else if (subject1.isEmpty())
                {
                    c_subject.setError("Enter Subject Email ");
                    c_subject.requestFocus();
                    return;
                }
                else if (message1.isEmpty())
                {
                    message.setError("Enter Subject Email ");
                    message.requestFocus();
                    return;
                }

                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);
                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"abdul.mughal0099@gmail.com"});
                sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT,subject1);
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,message1);
                startActivity(Intent.createChooser(sendEmail, "Send mail..."));
            }
        });
    }
}