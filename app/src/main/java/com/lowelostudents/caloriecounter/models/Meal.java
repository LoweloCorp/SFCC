package com.lowelostudents.caloriecounter.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
    private NutrientService nutrientService = NutrientService.getNutrientService();

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
}
