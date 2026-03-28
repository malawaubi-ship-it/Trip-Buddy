package com.example.tripbuddy.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tripbuddy.R;
import com.example.tripbuddy.databinding.ActivityHomeBinding;

public class HomeFragment extends Fragment {
    private static final String PREFS_NAME = "TripBuddyPrefs";
    private ActivityHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityHomeBinding.inflate(inflater, container, false);

        // Display logged-in username
        if (getActivity() != null) {
            SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String username = prefs.getString("username", "Guest");
            binding.usernameTextView.setText("Logged in as: " + username);
        }

        // Plan Trip button (navigates to TripPlanningActivity, keeping it as activity for now)
        binding.planTripButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TripPlanningActivity.class);
            startActivity(intent);
        });

        // Memories button 
        binding.memoriesButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.memoriesFragment);
        });

        // Gallery button 
        binding.galleryButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.galleryFragment);
        });

        // Settings button 
        binding.settingsButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.settingsFragment);
        });

        // Logout button
        binding.logoutButton.setOnClickListener(v -> logout());

        return binding.getRoot();
    }

    private void logout() {
        if (getActivity() != null) {
            SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.remove("username");
            editor.apply();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
