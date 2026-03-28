package com.example.tripbuddy.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;
import com.example.tripbuddy.data.db.AppDatabase;
import com.example.tripbuddy.data.model.Memory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MemoriesActivity extends AppCompatActivity {
    private static final String TAG = "MemoriesActivity";
    private MediaPlayer mediaPlayer;
    private EditText memoryTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);

        memoryTextEditText = findViewById(R.id.memoryTextEditText);
        Button playButton = findViewById(R.id.playButton);
        Button pauseButton = findViewById(R.id.pauseButton);
        Button stopButton = findViewById(R.id.stopButton);
        Button saveMemoryButton = findViewById(R.id.saveMemoryButton);

        if (playButton == null || pauseButton == null || stopButton == null || saveMemoryButton == null) {
            Log.e(TAG, "One or more buttons not found");
            Toast.makeText(this, "UI error", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.sample_music);
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
            String text = memoryTextEditText.getText().toString().trim();
            if (!text.isEmpty()) {
                Memory memory = new Memory(
                        text, "path/to/image", "Location", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()), "Happy", "path/to/music"
                );
                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(this);
                    db.memoryDao().insert(memory);
                    runOnUiThread(() -> Toast.makeText(this, "Memory saved", Toast.LENGTH_SHORT).show());
                }).start();
            } else {
                Toast.makeText(this, "Enter memory text", Toast.LENGTH_SHORT).show();
            }
        });
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