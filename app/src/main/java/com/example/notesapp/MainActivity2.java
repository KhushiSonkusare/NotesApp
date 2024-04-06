    package com.example.notesapp;

    import android.app.AlertDialog;
    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Build;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Spinner;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.NotificationCompat;

    public class MainActivity2 extends AppCompatActivity {

        EditText editTextName, editTextEmail;
        Spinner spinnerBranch, spinnerYear;
        Button nextButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            editTextName = findViewById(R.id.editTextName);
            editTextEmail = findViewById(R.id.editTextEmail);
            spinnerBranch = findViewById(R.id.spinnerBranch);
            spinnerYear = findViewById(R.id.spinnerYear);
            nextButton = findViewById(R.id.nextButton);

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateForm()) {
                        insertUserDetails();
                        Intent intent = new Intent(MainActivity2.this, NotesActivity.class);
                        String branch = spinnerBranch.getSelectedItem().toString();
                        intent.putExtra("UserBranch", branch);
                        startActivity(intent);

                        sendNotification();
                        Toast.makeText(MainActivity2.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        showErrorDialog("Please fill all fields and fill details correctly.");
                    }
                }
            });
        }

        private boolean validateForm() {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String branch = spinnerBranch.getSelectedItem() != null ? spinnerBranch.getSelectedItem().toString() : "";
            String year = spinnerYear.getSelectedItem() != null ? spinnerYear.getSelectedItem().toString() : "";

            boolean isNameValid = !name.isEmpty() && !name.matches(".*\\d.*");
            if (!isNameValid) {
                showErrorDialog("Name cannot contain numeric values.");
            }
            boolean isEmailValid = !email.isEmpty() && email.contains("@") && email.contains(".");

            boolean isEmailPatternValid = email.matches("[a-zA-Z][a-zA-Z0-9._-]*@[a-zA-Z0-9._-]+\\.[a-zA-Z]{2,4}");
            boolean isBranchAndYearValid = !branch.isEmpty() && !year.isEmpty();
            if (!isEmailPatternValid || !isEmailValid) {
                showErrorDialog("Incorrect email.");
            }
            return isNameValid && isEmailValid && isEmailPatternValid && isBranchAndYearValid;
        }

        private void insertUserDetails() {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String branch = spinnerBranch.getSelectedItem().toString();
            String year = spinnerYear.getSelectedItem().toString();

            SQLiteDatabase db = openOrCreateDatabase("UserDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR, email VARCHAR, branch VARCHAR, year VARCHAR);");
            String query = "INSERT INTO users(name, email, branch, year) VALUES ('" + name + "', '" + email + "', '" + branch + "', '" + year + "')";
            db.execSQL(query);
            db.close();
        }

        private void sendNotification() {
            String channelId = "registration_channel";
            String channelName = "Registration Channel";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.notification_icon)
                    .setContentTitle("Registration Successful")
                    .setContentText("You have successfully registered")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(1, builder.build());
            }
        }

        private void showErrorDialog(String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }
