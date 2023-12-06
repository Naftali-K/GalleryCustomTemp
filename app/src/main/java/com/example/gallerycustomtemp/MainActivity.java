package com.example.gallerycustomtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";
    private static final int PERMISSION_REQ_CODE = 100;

    private Button youtube1Btn, chatGptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();
//        checkPermissions();

        youtube1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), DisplayPhotosFromGalleryActivity.class));
            }
        });
        chatGptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), FromChatGPTActivity.class));
            }
        });
    }

    private void setReferences() {
        youtube1Btn = findViewById(R.id.youtube1_btn);
        chatGptBtn = findViewById(R.id.chat_gpt_btn);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermissions: READ_EXTERNAL_STORAGE Permission already granted.");
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermissions: WRITE_EXTERNAL_STORAGE Permission already granted.");
        }



        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermissions: READ_MEDIA_IMAGES Permission already granted.");
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_VIDEO}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermissions: READ_MEDIA_VIDEO Permission already granted.");
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO}, PERMISSION_REQ_CODE);
        } else {
            Log.d(TAG, "checkPermissions: READ_MEDIA_AUDIO Permission already granted.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Have permission for read files.");
            }
        }
    }
}