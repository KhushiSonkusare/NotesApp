package com.example.notesapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConnectWithFriendsActivity extends AppCompatActivity {

    private LinearLayout layout;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_friends);

        layout = findViewById(R.id.friends_layout);

        db = openOrCreateDatabase("UserDB", MODE_PRIVATE, null);

        String userBranch = getIntent().getStringExtra("UserBranch");

        String query = "SELECT DISTINCT t.name, t.email FROM users AS t " +
                "JOIN users AS s ON t.branch = s.branch AND t.year = s.year " +
                "WHERE t.branch = ?";

        Cursor cursor = db.rawQuery(query, new String[]{userBranch});

        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("name");
            int emailIndex = cursor.getColumnIndex("email");

            while (!cursor.isAfterLast()) {
                if (nameIndex != -1 && emailIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    String email = cursor.getString(emailIndex);
                    addFriendTextView(name, email);
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    private void addFriendTextView(String name, String email) {
        TextView textView = new TextView(this);
        textView.setText(name + "       ");
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // Adjust the text size as needed

        // Initialize textViewClickListener
        View.OnClickListener textViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This block of code intentionally left blank
            }
        };

        textView.setOnClickListener(textViewClickListener);


        Button emailButton = new Button(this);
        emailButton.setText(email);
        emailButton.setTextColor(Color.WHITE);
        emailButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        emailButton.setOnClickListener(emailButtonClickListener);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        emailButton.setLayoutParams(params);

        layout.addView(textView);
        layout.addView(emailButton);
    }


    View.OnClickListener emailButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = ((Button) v).getText().toString();
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Log.d("Email", "Sending email to: " + email);
                sendEmail(email);
            }
        }
    };

    private void sendEmail(String emailAddress) {
        String defaultSubject = "Hello!";
        String defaultMessage = "This is a default message.";

        Uri uri = Uri.parse("mailto:" + Uri.encode(emailAddress));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, defaultSubject);
        intent.putExtra(Intent.EXTRA_TEXT, defaultMessage);

        startActivity(intent);
    }
}
