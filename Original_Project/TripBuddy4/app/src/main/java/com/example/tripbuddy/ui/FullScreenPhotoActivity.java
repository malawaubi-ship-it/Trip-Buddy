package com.example.tripbuddy.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;

import com.example.tripbuddy.databinding.ActivityFullScreenPhotoBinding;

public class FullScreenPhotoActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private ActivityFullScreenPhotoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullScreenPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView fullScreenImage = binding.fullScreenImage;
        Button playMusicButton = binding.playMusicButton;
        Button stopMusicButton = binding.stopMusicButton;

        if (fullScreenImage != null) fullScreenImage.setImageResource(R.drawable.sample_image);

        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.sample_music);
            if (mediaPlayer == null) {
                throw new IllegalStateException("Music file not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (playMusicButton != null) {
            playMusicButton.setOnClickListener(v -> {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) mediaPlayer.start();
            });
        }
        if (stopMusicButton != null) {
            stopMusicButton.setOnClickListener(v -> {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.prepareAsync();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}