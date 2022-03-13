package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.interfaces.MealDao;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;

import lombok.Getter;

public class MealRepo extends CRUDRepository<Meal> {
    @Getter
    private final LiveData<List<Meal>> meals;
    @Getter
    private final LiveData<List<Meal_Food_Relation>> meal_foods;

    public MealRepo(Context context) {
        super(context);
        super.setCrudDao(getAppdb().mealDao());
        final MealDao mealDao = (MealDao) super.getCrudDao();
        meals = mealDao.getAllObservable();
        meal_foods = mealDao.getAllObservableTransaction();
    }
}
