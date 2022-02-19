package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;
import com.lowelostudents.caloriecounter.services.NutrientService;

import java.sql.SQLDataException;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Meal extends Nutrients {
    @PrimaryKey(autoGenerate = true)
    private long mealId;
    @Ignore
    private NutrientService nutrientService = NutrientService.getInstance();

    @Ignore
    public Meal() {
    }

    //TODO Test whether necessary on update

    public Meal(String name, List<Food> foodList, long[] proofOfExistence) throws SQLDataException {
        for (int i = 0; i < proofOfExistence.length; i++) {
            if (proofOfExistence[i] == 0)
                throw new SQLDataException("Food at index " + i + " doesn't exist in Database. Its foodId is 0");
        }
        this.name = name;
        nutrientService.calcCalories(this, foodList);
    }

    public Meal(String name) {
        this.name = name;
    }

    @Dao
    public abstract static class MealDao extends CRUDDao<Meal, Meal_Food_Relation> {
        @Query("SELECT * FROM Meal")
        public abstract LiveData<List<Meal>> getAllObservable();

        @Query("SELECT * FROM Meal WHERE mealId = :mealId")
        public abstract LiveData<Meal> getObservable(long mealId);

        @Transaction
        @Query("SELECT * FROM Meal")
        public abstract LiveData<List<Meal_Food_Relation>> getAllObservableTransaction();

        @Transaction
        @Query("SELECT * FROM Meal WHERE mealId = :mealId")
        public abstract LiveData<Meal_Food_Relation> getObservableTransaction(long mealId);
    }
}
