package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import lombok.Data;

@Entity(primaryKeys = {"dayId", "foodId"},
        indices = {
                @Index(value = "dayId", unique = true),
                @Index(value = "foodId", unique = true)
        })
@Data
public class Day_Food {
    private long foodId;
    private int dayId;

    @Dao
    public abstract static class Day_FoodDao extends CRUDDao<Day_Food, Object> {
    }
}
