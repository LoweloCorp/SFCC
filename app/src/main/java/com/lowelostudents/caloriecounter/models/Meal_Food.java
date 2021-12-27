package com.lowelostudents.caloriecounter.models;

import androidx.room.Entity;

import lombok.Data;

@Entity(primaryKeys = {"mealId", "foodId"})
@Data
public class Meal_Food {
    private long mealId;
    private long foodId;
}
