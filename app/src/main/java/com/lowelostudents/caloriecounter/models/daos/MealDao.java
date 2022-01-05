package com.lowelostudents.caloriecounter.models.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lowelostudents.caloriecounter.models.Meal;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    long[] insertall(Meal... meal);

    @Query("SELECT * FROM Meal")
    List<Meal> getAll();

    @Query("SELECT * FROM Meal WHERE mealId = :mealId")
    Meal getOne(long mealId);

    @Update
    void updateOne(Meal meal);

    @Update
    void updateAll(Meal... meals);

    @Delete
    void deleteOne(Meal meal);

    @Delete
    void deleteAll(Meal... meals);

}
