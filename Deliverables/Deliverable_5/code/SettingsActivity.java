package com.example.tripbuddy.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "TripBuddyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        CheckBox darkModeCheckBox = findViewById(R.id.darkModeCheckBox);
        CheckBox musicToggleCheckBox = findViewById(R.id.musicToggleCheckBox);
        Button saveSettingsButton = findViewById(R.id.saveSettingsButton);

        if (darkModeCheckBox != null && musicToggleCheckBox != null && saveSettingsButton != null) {
            darkModeCheckBox.setChecked(prefs.getBoolean("darkMode", false));
            musicToggleCheckBox.setChecked(prefs.getBoolean("musicEnabled", true));

            saveSettingsButton.setOnClickListener(v -> {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("darkMode", darkModeCheckBox.isChecked());
                editor.putBoolean("musicEnabled", musicToggleCheckBox.isChecked());
                editor.apply();
                finish();
            });
        }
    }
}