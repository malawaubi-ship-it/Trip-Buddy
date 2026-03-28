package com.example.tripbuddy.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tripbuddy.databinding.ActivitySettingsBinding;

public class SettingsFragment extends Fragment {
    private static final String PREFS_NAME = "TripBuddyPrefs";
    private ActivitySettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivitySettingsBinding.inflate(inflater, container, false);

        if (getActivity() != null) {
            SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            binding.darkModeCheckBox.setChecked(prefs.getBoolean("darkMode", false));
            binding.musicToggleCheckBox.setChecked(prefs.getBoolean("musicEnabled", true));

            binding.saveSettingsButton.setOnClickListener(v -> {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("darkMode", binding.darkModeCheckBox.isChecked());
                editor.putBoolean("musicEnabled", binding.musicToggleCheckBox.isChecked());
                editor.apply();
                // We no longer call finish() since it's a Fragment in Bottom Navigation
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
