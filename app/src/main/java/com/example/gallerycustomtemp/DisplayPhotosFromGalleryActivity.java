package com.example.gallerycustomtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gallerycustomtemp.adapters.GalleryRecyclerViewAdapter;

import java.util.List;

/**
 * https://youtu.be/qOlkLTOa_Ig
 * https://github.com/bumptech/glide
 */
public class DisplayPhotosFromGalleryActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";
    private static final int MY_READ_PERMISSION_CODE = 100;

    private TextView gallery_number;
    private RecyclerView recyclerView;
    private GalleryRecyclerViewAdapter galleryAdapter;

    private List<String> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photos_from_gallery);
        setReferences();

        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DisplayPhotosFromGalleryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        } else {
          loadImages();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getBaseContext(), "Read external storage permission granted", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onRequestPermissionsResult: Read external storage permission granted. Can read.");
                loadImages();
            } else {
                Toast.makeText(getBaseContext(), "Read external storage permission denied", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onRequestPermissionsResult: Read external storage permission denied. FAIL.");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, MY_READ_PERMISSION_CODE);
//                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
            }
        }
    }

    private void setReferences() {
        gallery_number = findViewById(R.id.gallery_number);
        recyclerView = findViewById(R.id.recyclerview_gallery_images);
    }

    private void loadImages() {
//        recyclerView.setHasFixedSize(true);

        images = ImagesGallery.listOfImages(this);

        galleryAdapter = new GalleryRecyclerViewAdapter(this, images, new GalleryRecyclerViewAdapter.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                Log.d(TAG, "onPhotoClick: Clicked image with uri: " + path);
            }
        });
        recyclerView.setAdapter(galleryAdapter);
        gallery_number.setText("Photos (" + images.size() + ")");
    }
}