package com.lowelostudents.caloriecounter.ui.foodhub;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.repositories.MealRepo;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.GenericViewModel;

import java.util.List;

import lombok.Getter;

public class MealViewModel extends GenericViewModel<Meal> {
    @Getter
    private LiveData<List<Meal>> meals;

    public MealViewModel(Application context) {
        super(context);
        setCrudRepository(new MealRepo(context.getApplicationContext()));
        final MealRepo mealRepo = (MealRepo) getCrudRepository();
        meals = mealRepo.getMeals();
    }
}