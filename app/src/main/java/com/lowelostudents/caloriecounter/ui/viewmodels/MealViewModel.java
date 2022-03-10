package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.repositories.DayMealRepo;
import com.lowelostudents.caloriecounter.data.repositories.MealFoodRepo;
import com.lowelostudents.caloriecounter.data.repositories.MealRepo;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.models.entities.Day_Meal;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Meal_Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MealViewModel extends AndroidViewModel {
    @Getter
    private final LiveData<List<Meal>> meals;
    private final MealRepo repo;
    private final DayMealRepo dayMealRepo;
    private final MealFoodRepo mealFoodRepo;
    private final MealRepo mealRepo;
    public final List<Food> checkedNutrients = new ArrayList<>();

    public MealViewModel(Application context) {
        super(context);
        this.repo = new MealRepo(context.getApplicationContext());
        this.dayMealRepo = new DayMealRepo(context.getApplicationContext());
        this.mealFoodRepo = new MealFoodRepo(context.getApplicationContext());
        this.mealRepo = new MealRepo(context.getApplicationContext());
        this.meals = repo.getMeals();
    }

    public Long insert(Meal t) {
        return repo.insert(t);
    }

    public Long insert (Nutrients meal) {
        Calendar cal = Calendar.getInstance();
        Day_Meal day_meal = new Day_Meal(meal.getId() ,cal.get(Calendar.DATE));
        return dayMealRepo.insert(day_meal);
    }

    public Long[] insert (String mealName) {
        Meal meal = new Meal(mealName, checkedNutrients);
        List<Meal_Food> meal_foods = new ArrayList<>();

        this.checkedNutrients.forEach( food -> {
            meal_foods.add(new Meal_Food(food.getId(), mealName));
        });

        this.mealRepo.insert(meal);
        return this.mealFoodRepo.insert(meal_foods);
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
