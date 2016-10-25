package com.example.jkloppenburg1.petprotector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.jkloppenburg1.petprotector.MainActivity.getUriToResource;
import static com.example.jkloppenburg1.petprotector.R.id.petImageView;

public class PetListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(petImageView);

        //
        imageURI = getUriToResource(this, R.drawable.none);

        petImageView.setImageURI(imageURI);

    }

    public void selectPetImage()
    {
        // List of all permissions
        ArrayList<String> permList = new ArrayList<>();

        // Check if we have permission to use camera
        int cameraPermission = ContextCompat.setSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.CAMERA);
        }

        int readExternalStoragePermission = ContextCompat.setSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        int writeExternalStoragePermission = ContextCompat.setSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
        {
            permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        // If the list has items (size > 0) request permissions from the user.
        int requestCode = 100;
        if (permList.size() > 0)
        {
            // Convert the array list into an array of strings
            String[] perms = new String[permList.size()];


            ActivityCompat.requestPermissions(this, permList.toArray(perms), requestCode);
        }

        if (cameraPermission == PackageManager.PERMISSION_GRANTED
                && readExternalStoragePermission == PackageManager.PERMISSION_GRANTED
                && writeExternalStoragePermission == PackageManager.PERMISSION_GRANTED)
        {

            // Use an intent to launch gallery and take pictures
            Intent galleryIntent = new Intent(this, Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, requestCode);

        }
        else
        {
            Toast.makeText(this, "Pet Protector requires camera and external storage permission",
                    Toast.LENGTH_LONG).show();
        }

    }

}
