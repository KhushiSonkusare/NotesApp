package com.example.notesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        // Find the "Connect with Friends" button
        Button connectWithFriendsButton = findViewById(R.id.connectWithFriendsButton);

        // Set an OnClickListener for the button
        connectWithFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectWithFriends();
            }
        });
    }

    // Method to open the ConnectWithFriendsActivity
    private void connectWithFriends() {
        Intent intent = new Intent(NotesActivity.this, ConnectWithFriendsActivity.class);
        startActivity(intent);
    }

    public void openOPPsNotes(View view) {
        openNotes("https://drive.google.com/drive/u/1/folders/1v3II2Tg4J9_qgjSH65NHq6AB2Nw-ZC8l");
    }

    public void openPhysicsNotes(View view) {
        openNotes("https://drive.google.com/drive/u/1/folders/1v3II2Tg4J9_qgjSH65NHq6AB2Nw-ZC8l");
    }

    public void openCWSNotes(View view) {
        openNotes("https://drive.google.com/drive/u/1/folders/15-B1RZA-dTZaqBDciHJX0DEa07sxIi3h");
    }

    public void openEnglishNotes(View view) {
        openNotes("https://drive.google.com/drive/u/1/folders/1qOq0v9uuHuxFUOEAEwDCdAugvTSAbg_r");
    }

    public void openCAONotes(View view) {
        openNotes("https://drive.google.com/drive/u/1/folders/1cmzQs2EBdhYikow8yYhvt7Ag3vtt4JY8");
    }

    private void openNotes(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
