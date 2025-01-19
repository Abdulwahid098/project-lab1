package com.example.plantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class privacypolicy extends AppCompatActivity {
    private Button home, contactUs,logout,privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_privacypolicy);

        home = findViewById(R.id.home);
        contactUs = findViewById(R.id.contactUs);
        logout = findViewById(R.id.logout);
        privacy=findViewById(R.id.privacy);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Home
                Intent intent = new Intent(privacypolicy.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ///privacy
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout and go to login activity
                Intent intent = new Intent(privacypolicy.this, privacypolicy.class);
                startActivity(intent);
                finish();
            }
        });
        // Logout Button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout and go to login activity
                Intent intent = new Intent(privacypolicy.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        // Contact Us Button
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Contact Us activity
                Intent intent = new Intent(privacypolicy.this, contact.class);
                startActivity(intent);

            }
        });

    }
}