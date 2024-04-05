package com.example.notesapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConnectWithFriendsActivity extends AppCompatActivity {

    private TextView friendsTextView;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_friends);

        friendsTextView = findViewById(R.id.friendsTextView);

        // Open or create the database
        db = openOrCreateDatabase("UserDB", MODE_PRIVATE, null);

        // Query to fetch all applications
        String query = "SELECT name, email FROM users"; // Corrected table name

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        StringBuilder friendListText = new StringBuilder();

        // Check if cursor is not null and it has data
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int emailIndex = cursor.getColumnIndex("email");

            // Loop through the cursor to fetch friend names and emails
            do {
                if (nameIndex != -1 && emailIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    String email = cursor.getString(emailIndex);
                    friendListText.append(name).append("    ").append(email).append("\n");
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Set the text of the TextView
        friendsTextView.setText(friendListText.toString());
    }
}
