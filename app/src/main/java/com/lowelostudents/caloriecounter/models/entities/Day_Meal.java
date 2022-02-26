package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import lombok.Data;

@Entity(primaryKeys = {"dayId", "id"},
        indices = {
                @Index(value = "dayId", unique = true),
                @Index(value = "id", unique = true)
        })
@Data

public class Day_Meal {
    private long id;
    private int dayId;

    public Day_Meal(long id, int dayId) {
        this.id = id;
        this.dayId = dayId;
    }

    @Dao
    public abstract static class Day_MealDao extends CRUDDao<Day_Meal> {
    }
}