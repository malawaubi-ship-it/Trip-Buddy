package com.example.tripbuddy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;

public class RegisterActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "TripBuddyPrefs";
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        View registerButton = findViewById(R.id.registerButton);
        if (registerButton != null) {
            registerButton.setOnClickListener(v -> {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (validateRegistration(username, password)) {
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("registeredUsername", username);
                    editor.putString("registeredPassword", password); // Store password (insecure for production; use hashing)
                    editor.apply();
                    Toast.makeText(this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Username must be at least 4 characters, password at least 6", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validateRegistration(String username, String password) {
        return username.length() >= 4 && password.length() >= 6; // Basic validation
    }
}