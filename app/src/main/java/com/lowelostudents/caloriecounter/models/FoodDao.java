package com.lowelostudents.caloriecounter.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    void insertAll(Food... foods);

    @Query("SELECT * FROM Food")
    List<Food> getFoods();

    @Query("SELECT * FROM Food WHERE foodId = :id")
    Food getFood(long id);

    @Update
    void updateFood(Food food);

    @Update
    void updateFoods(List<Food> foods);

    @Delete
    void deleteFood(Food food);
}
