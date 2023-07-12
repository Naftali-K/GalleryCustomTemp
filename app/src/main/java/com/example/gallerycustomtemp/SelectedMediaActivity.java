package com.example.gallerycustomtemp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.gallerycustomtemp.adapters.MediaSelectedAdapter;

public class SelectedMediaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MediaSelectedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_media);
        setReferences();

        adapter = new MediaSelectedAdapter(getBaseContext(), DataManager.getSelectedMediaItemList());
        recyclerView.setAdapter(adapter);
    }

    private void setReferences() {
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        DataManager.getSelectedMediaItemList().clear();
    }
}