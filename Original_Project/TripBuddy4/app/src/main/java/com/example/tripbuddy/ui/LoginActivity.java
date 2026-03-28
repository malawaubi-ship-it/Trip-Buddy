package com.example.tripbuddy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;
import com.example.tripbuddy.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "TripBuddyPrefs";
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        binding.registerButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        binding.loginButton.setOnClickListener(v -> {
            String username = binding.usernameEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();
            SharedPreferences prefsCheck = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String registeredUsername = prefsCheck.getString("registeredUsername", "");
            String registeredPassword = prefsCheck.getString("registeredPassword", "");

            if (validateLogin(username, password, registeredUsername, registeredPassword)) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username);
                editor.putBoolean("isLoggedIn", true);
                editor.apply();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateLogin(String username, String password, String registeredUsername, String registeredPassword) {
        return username.equals(registeredUsername) && password.equals(registeredPassword) && !username.isEmpty();
    }
}