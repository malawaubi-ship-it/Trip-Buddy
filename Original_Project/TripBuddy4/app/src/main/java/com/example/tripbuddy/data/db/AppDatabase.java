package com.example.tripbuddy.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.tripbuddy.data.model.Memory;
import com.example.tripbuddy.data.model.Trip;
import com.example.tripbuddy.data.db.dao.MemoryDao;
import com.example.tripbuddy.data.db.dao.TripDao;

@Database(entities = {Trip.class, Memory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TripDao tripDao();
    public abstract MemoryDao memoryDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(android.content.Context context) {
        if (INSTANCE == null) {
            INSTANCE = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "tripbuddy_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}