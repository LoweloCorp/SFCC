package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Day_Meal;

public class DayMealRepo extends CRUDRepository<Day_Meal> {
    public DayMealRepo(Context context) {
        super(context);
        super.setCrudDao(getAppdb().day_mealDao());
    }
}
