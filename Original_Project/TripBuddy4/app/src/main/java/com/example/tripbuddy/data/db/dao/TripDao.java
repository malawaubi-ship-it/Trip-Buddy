package com.example.tripbuddy.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.tripbuddy.data.model.Trip;
import java.util.List;

@Dao
public interface TripDao {
    @Insert void insert(Trip trip);
    @Query("SELECT * FROM trips") List<Trip> getAllTrips();
    @Query("SELECT COUNT(*) FROM trips") int getTripCount();
}