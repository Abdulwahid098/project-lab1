package com.example.plantapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class health extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private Button home,logout,contactUs, linkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        imageView = findViewById(R.id.resultImageView);
        textView = findViewById(R.id.resultTextView);
        home=findViewById(R.id.home);
        logout=findViewById(R.id.logout);
        contactUs=findViewById(R.id.contactUs);
        linkButton=findViewById(R.id.linkButton);
        // Get the image and result text from the intent
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        String resultText = getIntent().getStringExtra("result");

        // Decode the image
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        // Set the image and result text
        imageView.setImageBitmap(image);
        textView.setText(resultText);

        ///home
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Home
                Intent intent = new Intent(health.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ///logout
        // Logout Button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout and go to login activity
                Intent intent = new Intent(health.this, login.class);
                startActivity(intent);
                finish();
            }
        });
        // Contact Us Button
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Contact Us activity
                Intent intent = new Intent(health.this, contact.class);
                startActivity(intent);
                finish();
            }
        });
        ///link
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.researchgate.net/figure/Sample-test-images-of-healthy-and-unhealthy-tomato-leaves_fig1_365870473"));
                startActivity(browserIntent);
            }
        });
    }
}
