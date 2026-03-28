package com.example.tripbuddy.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.tripbuddy.data.model.Memory;
import java.util.List;

@Dao
public interface MemoryDao {
    @Insert
    void insert(Memory memory);

    @Query("SELECT * FROM memories")
    List<Memory> getAllMemories();
}