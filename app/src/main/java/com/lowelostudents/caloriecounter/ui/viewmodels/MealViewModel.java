package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.repositories.DayMealRepo;
import com.lowelostudents.caloriecounter.data.repositories.MealRepo;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.models.entities.Day_Meal;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;

import java.util.Calendar;
import java.util.List;

import lombok.Getter;

public class MealViewModel extends AndroidViewModel {
    @Getter
    private final LiveData<List<Meal>> meals;
    private final MealRepo repo;
    private final DayMealRepo dayMealRepo;

    public MealViewModel(Application context) {
        super(context);
        repo = new MealRepo(context.getApplicationContext());
        dayMealRepo = new DayMealRepo(context.getApplicationContext());
        meals = repo.getMeals();
    }

    public Long insert(Meal t) {

        return repo.insert(t);
    }

    public Long insert (Nutrients meal) {
        Calendar cal = Calendar.getInstance();
        Day_Meal day_meal = new Day_Meal(meal.getId() ,cal.get(Calendar.DATE));
        return dayMealRepo.insert(day_meal);
    }

    public void insertAll(List<Meal> t) {
        repo.insert(t);
    }

    public void update(Meal t) {
        repo.update(t);
    }

    public void updateAll(List<Meal> t) {
        repo.update(t);
    }

    public void delete(Meal t) {
        repo.delete(t);
    }

    public void deleteAll(Meal t) {
        repo.delete(t);
    }
}
