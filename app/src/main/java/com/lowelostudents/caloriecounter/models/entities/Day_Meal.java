package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import lombok.Data;

@Entity(indices = {
                @Index(value = "dayId", unique = false),
                @Index(value = "id", unique = false)
        })

@Data
public class Day_Meal {
    @PrimaryKey(autoGenerate = true)
    private long primaryKey;
    private long id;
    private int dayId;

    public Day_Meal(long id, int dayId) {
        this.id = id;
        this.dayId = dayId;
    }
}