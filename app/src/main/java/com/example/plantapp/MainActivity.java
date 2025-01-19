package com.example.plantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plantapp.ml.Model; // Ensure that you have your model file

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    private Button select, predict, logout,privacy;
    private TextView textView;
    private ImageView imageView;
    private Bitmap img;
    int imageSize = 224;  // Resize to 224x224
    private Button home, contactUs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        select = findViewById(R.id.select);
        predict = findViewById(R.id.predict);
        textView = findViewById(R.id.textId);
        imageView = findViewById(R.id.imageview);
        home = findViewById(R.id.home);
        contactUs = findViewById(R.id.contactUs);
        logout = findViewById(R.id.logout);
        privacy=findViewById(R.id.privacy);

        // Home Button
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Home
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ///privacy
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout and go to login activity
                Intent intent = new Intent(MainActivity.this, privacypolicy.class);
                startActivity(intent);

            }
        });


        // Logout Button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout and go to login activity
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        // Contact Us Button
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Contact Us activity
                Intent intent = new Intent(MainActivity.this, contact.class);
                startActivity(intent);
             
            }
        });

        // Select Image Button
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        // Predict Button
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img == null) {
                    textView.setText("Please select an image first.");
                    return;
                }

                // Resize image to 224x224
                img = Bitmap.createScaledBitmap(img, imageSize, imageSize, true);

                try {
                    Model model = Model.newInstance(getApplicationContext());

                    // Define input tensor with 224x224 size
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, imageSize, imageSize, 3}, DataType.FLOAT32);

                    // Create tensor image from resized bitmap
                    TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                    tensorImage.load(img);
                    ByteBuffer byteBuffer = tensorImage.getBuffer();

                    // Load buffer into tensor
                    inputFeature0.loadBuffer(byteBuffer);

                    // Run model inference
                    Model.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Get confidence values from output tensor
                    float[] confidences = outputFeature0.getFloatArray();

                    // Close model after use
                    model.close();

                    // Determine the result
                    String resultText = "";
                    Intent intent;
                    if (confidences[0] > confidences[1]) {
                        resultText = "Diseased Tomato leaf";
                        // Pass to DiseasedActivity
                        intent = new Intent(MainActivity.this, diease.class);



                    } else {
                        resultText = " Healthy Tomato leaf";
                        // Pass to HealthyActivity
                        intent = new Intent(MainActivity.this,health.class);

                    }

                    // Convert Bitmap to ByteArray
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    img.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    // Add image and result to the intent
                    intent.putExtra("image", byteArray);
                    intent.putExtra("result", resultText);

                    // Start the target activity
                    startActivity(intent);
//                    finish();

                } catch (IOException e) {
                    e.printStackTrace();
                    textView.setText("Error: Model inference failed.");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageView.setImageURI(uri); // Set selected image to ImageView
            } catch (IOException e) {
                e.printStackTrace();
                textView.setText("Error loading image.");
            }
        }
    }
}
