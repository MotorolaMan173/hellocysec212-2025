package edu.gannon.photoapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    Button takePhoto;
    Button savePhoto;
    ImageView imageTaken;

    private static final int REQUEST_PERMISSION_STORAGE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        takePhoto = findViewById(R.id.btn_takePhoto);
        savePhoto = findViewById(R.id.btn_savePhoto);
        imageTaken = findViewById(R.id.img_imageTaken);

    }


    public void takePicture(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            saveToStorage();
        }
    }

    private void saveToStorage() {
        
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Permissions Requiered to take photos", Toast.LENGTH_LONG).show();

        }
        ActivityCompat.requestPermissions(
               this,
               new String[]{Manifest.permission.CAMERA},
               REQUEST_PERMISSION_CAMERA

        );
    }


    public void savePicture(View view) {
    }


}