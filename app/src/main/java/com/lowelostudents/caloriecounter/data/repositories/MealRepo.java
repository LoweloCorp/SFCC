package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.models.entities.Meal;

import java.util.List;

import lombok.Getter;

public class MealRepo extends CrudRepository<Meal>{
    @Getter
    private final LiveData<List<Meal>> meals;

    public MealRepo(Context context) {
        super(context);
        setCrudDao(getAppdb().mealDao());
        final Meal.MealDao mealDao = (Meal.MealDao) getCrudDao();
        meals = mealDao.getAllObservable();
    }
}
