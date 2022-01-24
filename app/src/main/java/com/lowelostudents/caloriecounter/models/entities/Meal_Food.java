package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.CrudDao;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;

import lombok.Data;

@Entity(primaryKeys = {"mealId", "foodId"})
@Data
public class Meal_Food {
    private long mealId;
    private long foodId;

    @Dao
    public interface  Meal_FoodDao extends CrudDao<Meal_Food> {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insertAll(Meal_Food... meal_foods);

        @Transaction
        @Query("SELECT * FROM Meal")
        List<Meal_Food_Relation> getAll();

        @Transaction
        @Query("SELECT * FROM Meal WHERE mealId = :mealId")
        Meal_Food_Relation getOne(long mealId);

        @Transaction
        @Query("SELECT * FROM Meal")
        LiveData<List<Meal_Food_Relation>> getAllObservable();

        @Transaction
        @Query("SELECT * FROM Meal WHERE mealId = :mealId")
        LiveData<Meal_Food_Relation> getObservable(long mealId);
    }
}