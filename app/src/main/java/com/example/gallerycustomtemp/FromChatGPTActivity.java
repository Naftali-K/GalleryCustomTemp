package com.example.gallerycustomtemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.example.gallerycustomtemp.adapters.MediaChatGTPAdapter;
import com.example.gallerycustomtemp.models.MediaItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FromChatGPTActivity extends AppCompatActivity {

    private Button addBtn;
    private RecyclerView recyclerView;
    private List<String> mediaList = new ArrayList<>();
    private List<MediaItem> mediaItemList = new ArrayList<>();
    private MediaChatGTPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_chat_gptactivity);
        setReferences();
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
}