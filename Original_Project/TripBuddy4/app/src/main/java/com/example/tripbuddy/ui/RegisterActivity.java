package com.example.tripbuddy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "TripBuddyPrefs";
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerButton.setOnClickListener(v -> {
            String username = binding.usernameEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();
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

    private boolean validateRegistration(String username, String password) {
        return username.length() >= 4 && password.length() >= 6; // Basic validation
    }
}