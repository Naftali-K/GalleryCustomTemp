package com.example.gallerycustomtemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gallerycustomtemp.adapters.MediaChatGTPAdapter;
import com.example.gallerycustomtemp.models.MediaItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FromChatGPTActivity extends AppCompatActivity {

    private static final String TAG = "Test_code";
    private static final int MY_READ_PERMISSION_CODE = 100;

    private Button addBtn;
    private RecyclerView recyclerView;
    private List<String> mediaList = new ArrayList<>();
    private List<MediaItem> mediaItemList = new ArrayList<>();
    private MediaChatGTPAdapter adapter;
    private String[] PERMISSIONS_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_chat_gptactivity);
        setReferences();
        PERMISSIONS_LIST = new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
        };

        requestPermissions();


        getMediaFromGallery();

//        adapter = new MediaChatGTPAdapter(getBaseContext(), mediaList);
        adapter = new MediaChatGTPAdapter(getBaseContext(), mediaItemList, new MediaChatGTPAdapter.NumberSelectedCallBack() {
            @Override
            public void selected(int selected) {
                if (selected > 0) {
                    addBtn.setEnabled(true);
                } else {
                    addBtn.setEnabled(false);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SelectedMediaActivity.class));
                finish();
            }
        });
    }

    private void setReferences() {
        addBtn = findViewById(R.id.add_btn);
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void getMediaFromGallery() {

        Uri contentUri = MediaStore.Files.getContentUri("external");
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + " IN (?, ?)";
        String[] selectionArgs = {
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)
        };
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

        Cursor cursor = getContentResolver().query(
                contentUri,
                null,
                selection,
                selectionArgs,
                sortOrder
        );

        if (cursor != null && cursor.moveToFirst()) {
//            int dataIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
//            do {
//                String mediaPath = cursor.getString(dataIndex);
//                mediaList.add(mediaPath);
//            } while (cursor.moveToNext());
//            cursor.close();

//            adapter.notifyDataSetChanged();

            int dataIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int displayNameIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
            int dateAddedIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED);
            int mediaTypeIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);
            int durationIndex = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION);

            do {
                String mediaPath = cursor.getString(dataIndex);
                String displayName = cursor.getString(displayNameIndex);
                long dateAdded = cursor.getLong(dateAddedIndex);
                int mediaType = cursor.getInt(mediaTypeIndex);
                Uri mediaUri = Uri.fromFile(new File(mediaPath));

                long duration = 0;
                int durationInSeconds = 0;
                int minutes = 0;
                int seconds = 0;

                MediaItem.MediaType mediaItemType;
                if (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
                    mediaItemType = MediaItem.MediaType.PHOTO;
                } else if (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                    mediaItemType = MediaItem.MediaType.VIDEO;
                    duration = cursor.getLong(durationIndex);
                    durationInSeconds = (int) (duration / 1000);
                    minutes = durationInSeconds / 60;
                    seconds = durationInSeconds % 60;

                } else {
                    continue; // Пропустить файлы с неизвестным типом медиа
                }

                MediaItem mediaItem = new MediaItem(displayName, mediaUri, dateAdded, mediaItemType);
                if (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                    mediaItem.setDuration(duration);
                    mediaItem.setDurationInSeconds(durationInSeconds);
                    mediaItem.setVideoDurationMinutes(minutes);
                    mediaItem.setVideoDurationSeconds(seconds);
                }

                mediaItemList.add(mediaItem);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private boolean hasPermission(String[] permissions) {

        if (permissions != null) {
            for (String permission: permissions) {
                if (ContextCompat.checkSelfPermission(getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    private void requestPermissions() {
        if (!hasPermission(PERMISSIONS_LIST)) {
            Log.d(TAG, "requestPermissions: ask list of permissions");
            ActivityCompat.requestPermissions(this, PERMISSIONS_LIST, MY_READ_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: All permissions ACCEPTED");
            }
        }
    }
}