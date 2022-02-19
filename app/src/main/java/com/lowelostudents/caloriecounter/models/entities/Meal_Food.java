package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import lombok.Data;

@Entity(primaryKeys = {"mealId", "foodId"}, indices = {
        @Index(value = "mealId", unique = true),
        @Index(value = "foodId", unique = true)
})

@Data
public class Meal_Food {
    private long mealId;
    private long foodId;

    @Dao
    public abstract static class Meal_FoodDao extends CRUDDao<Meal_Food, Object> {
    }
}
