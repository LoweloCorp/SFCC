package com.lowelostudents.caloriecounter.models.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lowelostudents.caloriecounter.models.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    void insertAll(Food... foods);

    @Query("SELECT * FROM Food")
    List<Food> getAll();

    @Query("SELECT * FROM Food WHERE foodId = :id")
    Food getOne(long id);

    @Update
    void updateOne(Food food);

    @Update
    void updateAll(Food... foods);

    @Delete
    void deleteOne(Food food);

    @Delete
    void deleteAll(Food... foods);
}
