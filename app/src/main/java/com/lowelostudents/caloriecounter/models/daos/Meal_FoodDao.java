package com.lowelostudents.caloriecounter.models.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.Meal_Food;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;

@Dao
public interface Meal_FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Meal_Food... meal_foods);

    @Transaction
    @Query("SELECT * FROM Meal")
    List<Meal_Food_Relation> getAll();

    @Transaction
    @Query("SELECT * FROM Meal WHERE mealId = :mealId")
    Meal_Food_Relation getOne(long mealId);
}
