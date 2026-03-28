package com.example.tripbuddy.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trips")
public class Trip {
    @PrimaryKey(autoGenerate = true) public int id;
    public String destination, startDate, endDate, notes, activities;
    public double totalCost;

    public Trip() {}
    public Trip(String destination, String startDate, String endDate, String notes, double totalCost, String activities) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.totalCost = totalCost;
        this.activities = activities;
    }
}