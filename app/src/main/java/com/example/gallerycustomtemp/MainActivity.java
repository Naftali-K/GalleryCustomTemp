package com.example.gallerycustomtemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button youtube1Btn, chatGptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setReferences();

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
}