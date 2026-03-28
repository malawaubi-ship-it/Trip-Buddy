package com.example.tripbuddy.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripbuddy.R;
import com.example.tripbuddy.data.db.AppDatabase;
import com.example.tripbuddy.data.model.Memory;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = findViewById(R.id.galleryRecyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

            // Load photo URLs from database
            new Thread(() -> {
                try {
                    AppDatabase db = AppDatabase.getInstance(this);
                    List<Memory> memories = db.memoryDao().getAllMemories();
                    String[] photoUrls = memories.stream()
                            .filter(memory -> memory.imagePath != null && !memory.imagePath.isEmpty())
                            .map(memory -> memory.imagePath)
                            .toArray(String[]::new);
                    runOnUiThread(() -> {
                        GalleryAdapter adapter = new GalleryAdapter(photoUrls);
                        recyclerView.setAdapter(adapter);
                    });
                } catch (Exception e) {
                    e.printStackTrace(); // Log error for debugging
                    runOnUiThread(() -> {
                        GalleryAdapter adapter = new GalleryAdapter(new String[0]); // Fallback to empty adapter
                        recyclerView.setAdapter(adapter);
                    });
                }
            }).start();
        } else {
            throw new IllegalStateException("galleryRecyclerView not found");
        }
    }
}