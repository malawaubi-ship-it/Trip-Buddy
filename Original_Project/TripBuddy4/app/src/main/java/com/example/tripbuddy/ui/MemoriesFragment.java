package com.example.tripbuddy.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tripbuddy.R;
import com.example.tripbuddy.data.db.AppDatabase;
import com.example.tripbuddy.data.model.Memory;
import com.example.tripbuddy.databinding.ActivityMemoriesBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MemoriesFragment extends Fragment {
    private static final String TAG = "MemoriesFragment";
    private MediaPlayer mediaPlayer;
    private EditText memoryTextEditText;
    private ActivityMemoriesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityMemoriesBinding.inflate(inflater, container, false);

        memoryTextEditText = binding.memoryTextEditText;
        Button playButton = binding.playButton;
        Button pauseButton = binding.pauseButton;
        Button stopButton = binding.stopButton;
        Button saveMemoryButton = binding.saveMemoryButton;

        if (playButton == null || pauseButton == null || stopButton == null || saveMemoryButton == null) {
            Log.e(TAG, "One or more buttons not found");
            if (getActivity() != null) Toast.makeText(getActivity(), "UI error", Toast.LENGTH_SHORT).show();
            return binding.getRoot();
        }

        try {
            if (getActivity() != null) mediaPlayer = MediaPlayer.create(getActivity(), R.raw.sample_music);
        } catch (Exception e) {
            Log.e(TAG, "MediaPlayer failed: " + e.getMessage());
            mediaPlayer = null;
        }

        playButton.setOnClickListener(v -> {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) mediaPlayer.start();
        });

        pauseButton.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) mediaPlayer.pause();
        });

        stopButton.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer.reset();
                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    Log.e(TAG, "Prepare failed: " + e.getMessage());
                }
            }
        });

        saveMemoryButton.setOnClickListener(v -> {
            v.performHapticFeedback(android.view.HapticFeedbackConstants.CONTEXT_CLICK);
            String text = memoryTextEditText.getText().toString().trim();
            if (!text.isEmpty()) {
                Memory memory = new Memory(
                        text, "path/to/image", "Location", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()), "Happy", "path/to/music"
                );
                new Thread(() -> {
                    if (getActivity() != null) {
                        AppDatabase db = AppDatabase.getInstance(getActivity());
                        db.memoryDao().insert(memory);
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Memory saved", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            } else {
                if (getActivity() != null) Toast.makeText(getActivity(), "Enter memory text", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        binding = null;
    }
}
