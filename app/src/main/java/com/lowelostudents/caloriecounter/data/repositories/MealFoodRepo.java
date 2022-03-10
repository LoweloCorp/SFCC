package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Meal_Food;

public class MealFoodRepo extends CRUDRepository<Meal_Food> {
    public MealFoodRepo(Context context) {
        super(context);
        super.setCrudDao(getAppdb().meal_foodDao());
    }
}
