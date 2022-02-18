package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;

import lombok.Data;

@Entity(primaryKeys = {"mealId", "foodId"})
@Data
public class Meal_Food {
    private long mealId;
    private long foodId;

    @Dao
    public interface  Meal_FoodDao extends CRUDDao<Meal_Food> {
        @Transaction
        @Query("SELECT * FROM Meal")
        List<Meal_Food_Relation> getAll();

        @Transaction
        @Query("SELECT * FROM Meal WHERE mealId = :mealId")
        Meal_Food_Relation getById(long mealId);

        @Transaction
        @Query("SELECT * FROM Meal")
        LiveData<List<Meal_Food_Relation>> getAllObservable();

        @Transaction
        @Query("SELECT * FROM Meal WHERE mealId = :mealId")
        LiveData<Meal_Food_Relation> getObservable(long mealId);
    }
}
