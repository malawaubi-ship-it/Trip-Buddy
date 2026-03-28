package com.example.tripbuddy.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "memories")
public class Memory {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String text;
    public String imagePath;
    public String location;
    public String date;
    public String mood;
    public String musicPath;

    public Memory(String text, String imagePath, String location, String date, String mood, String musicPath) {
        this.text = text;
        this.imagePath = imagePath;
        this.location = location;
        this.date = date;
        this.mood = mood;
        this.musicPath = musicPath;
    }
}