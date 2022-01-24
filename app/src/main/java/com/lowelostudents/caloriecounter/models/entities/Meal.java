package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.services.NutrientService;

import java.sql.SQLDataException;
import java.util.List;

import lombok.Data;

@Entity
@Data
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private long mealId;
    private String name;
    private int calTotal;
    private int gramTotal;
    @Ignore
    private NutrientService nutrientService = NutrientService.getInstance();

    @Ignore
    public Meal() {

    }

    public Meal(String name, List<Food> foodList, long[] proofOfExistence) throws SQLDataException {
        for (int i = 0; i < proofOfExistence.length; i++) {
            if (proofOfExistence[i] == 0) throw new SQLDataException("Food at index "+ i +" doesn't exist in Database. Its foodId is 0");
        }
        this.name = name;
        nutrientService.calcCalories(this, foodList);
    }

    public Meal(String name){
        this.name = name;
    }

    @Dao
    public interface MealDao extends CRUDDao<Meal> {
        @Query("SELECT * FROM Meal WHERE mealId = :mealId")
        Meal getById(long mealId);

        @Query("SELECT * FROM Meal")
        LiveData<List<Meal>> getAllObservable();

        @Query("SELECT * FROM Meal WHERE mealId = :mealId")
        LiveData<Meal> getObservable(long mealId);
    }
}
