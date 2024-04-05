package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getStartedButton = findViewById(R.id.getStartedButton);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showToast("Get Started button clicked!");
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
