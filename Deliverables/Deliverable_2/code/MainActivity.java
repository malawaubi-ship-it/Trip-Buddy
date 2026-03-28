package com.example.tripbuddy.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import com.example.tripbuddy.R;
import com.example.tripbuddy.data.db.AppDatabase;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private NavController navController; // Moved to class level to fix scoping issue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Room database
        db = getDatabase(this);

        // Set up navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController(); // Assign to class-level variable
            NavigationUI.setupActionBarWithNavController(this, navController);
        } else {
            throw new IllegalStateException("NavHostFragment not found");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null) {
            return navController.navigateUp() || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }

    public static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "tripbuddy_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    // Getter for database (optional, for use in fragments or other activities)
    public AppDatabase getDb() {
        return db;
    }
}