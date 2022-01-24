package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Meal_Food;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;

import lombok.Getter;

public class MealFoodRepo extends CRUDRepository<Meal_Food> {
    @Getter
    private final LiveData<List<Meal_Food_Relation>> meal_foods;

    MealFoodRepo(Context context) {
        super(context);
        setCrudDao(getAppdb().meal_foodDao());
        final Meal_Food.Meal_FoodDao meal_foodDao = (Meal_Food.Meal_FoodDao) getCrudDao();
        meal_foods = meal_foodDao.getAllObservable();
    }
}
