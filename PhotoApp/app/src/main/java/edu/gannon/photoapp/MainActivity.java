package edu.gannon.photoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    Button takePhoto;
    Button savePhoto;
    ImageView imageTaken;

    private boolean photoTaken = false;
    private String photoPath;


    private static final int REQUEST_PERMISSION_STORAGE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 2;
    private static final int REQUEST_PICTURE = 3;



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

    public void savePicture(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestStoragePermission();
        } else {
            saveToPhotoGallery();
        }
    }

    private void saveToStorage() {
        
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Permissions Required to take photos", Toast.LENGTH_LONG).show();

        }
        ActivityCompat.requestPermissions(
               this,
               new String[]{Manifest.permission.CAMERA},
               REQUEST_PERMISSION_CAMERA

        );
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Permission Required to save photos", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_PERMISSION_STORAGE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] resultCode, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, resultCode, grantResults);

        if (requestCode == REQUEST_PERMISSION_CAMERA){
            if (grantResults.length == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveToStorage();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void saveToPhotoGallery() {

        Intent photoIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File photo = new File(photoPath);
        Uri contentUri = Uri.fromFile(photo);
        photoIntent.setData(contentUri);
        this.sendBroadcast(photoIntent);

        Toast.makeText(this, "Saved Photo to Gallery", Toast.LENGTH_LONG).show();
    }
}