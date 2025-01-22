package com.example.plantapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class healthlydisease extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthlydisease);

        imageView = findViewById(R.id.resultImageView);
        textView = findViewById(R.id.resultTextView);

        // Get the image and result text from the intent
        byte[] byteArray = getIntent().getByteArrayExtra("image");
        String resultText = getIntent().getStringExtra("result");

        // Decode the image
        Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        // Set the image and result text
        imageView.setImageBitmap(image);
        textView.setText(resultText);
    }

    public void onBackPressed() {
        super.onBackPressed();  // This will allow going back to the previous activity (MainActivity)
    }
}
