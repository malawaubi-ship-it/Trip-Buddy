package com.example.tripbuddy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;

public class HomeActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "TripBuddyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Display logged-in username
        TextView usernameTextView = findViewById(R.id.usernameTextView);
        if (usernameTextView != null) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String username = prefs.getString("username", "Guest");
            usernameTextView.setText("Logged in as: " + username);
        } else {
            throw new IllegalStateException("Username TextView not found in activity_home.xml");
        }

        // Plan Trip button (navigates to TripPlanningActivity)
        View planTripButton = findViewById(R.id.planTripButton);
        if (planTripButton != null) {
            planTripButton.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, TripPlanningActivity.class);
                startActivity(intent);
            });
        } else {
            throw new IllegalStateException("Plan Trip button not found in activity_home.xml");
        }

        // Memories button (navigates to MemoriesActivity)
        View memoriesButton = findViewById(R.id.memoriesButton);
        if (memoriesButton != null) {
            memoriesButton.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, MemoriesActivity.class);
                startActivity(intent);
            });
        } else {
            throw new IllegalStateException("Memories button not found in activity_home.xml");
        }

        // Gallery button (navigates to GalleryActivity)
        View galleryButton = findViewById(R.id.galleryButton);
        if (galleryButton != null) {
            galleryButton.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
                startActivity(intent);
            });
        } else {
            throw new IllegalStateException("Gallery button not found in activity_home.xml");
        }

        // Settings button (navigates to SettingsActivity)
        View settingsButton = findViewById(R.id.settingsButton);
        if (settingsButton != null) {
            settingsButton.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            });
        } else {
            throw new IllegalStateException("Settings button not found in activity_home.xml");
        }

        // Logout button (navigates back to LoginActivity)
        View logoutButton = findViewById(R.id.logoutButton);
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> logout());
        } else {
            throw new IllegalStateException("Logout button not found in activity_home.xml");
        }
    }

    private void logout() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.remove("username");
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}