package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.repositories.DayMealRepo;
import com.lowelostudents.caloriecounter.data.repositories.MealFoodRepo;
import com.lowelostudents.caloriecounter.data.repositories.MealRepo;
import com.lowelostudents.caloriecounter.databinding.ActivityMainBinding;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.models.entities.Day_Meal;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Meal_Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    public final HashMap<Integer, Food> checkedNutrients = new HashMap<>();



    public MealViewModel(Application context) {
        super(context);
        this.repo = new MealRepo(context.getApplicationContext());
        this.dayMealRepo = new DayMealRepo(context.getApplicationContext());
        this.mealFoodRepo = new MealFoodRepo(context.getApplicationContext());
        this.mealRepo = new MealRepo(context.getApplicationContext());
        this.meals = repo.getMeals();
    }

    public void addToDay(Meal meal) {
        Calendar cal = Calendar.getInstance();
        Day_Meal day_meal = new Day_Meal(meal.getId() ,cal.get(Calendar.DATE));

        dayMealRepo.insert(day_meal);
    }

    public void removeFromDay(Meal meal) {
        dayMealRepo.delete(Day_Meal.class, meal.getId());
    }

    public Long[] insert (String mealName) {
        final List<Food> foods = new ArrayList<>(checkedNutrients.values());
        Meal meal = new Meal(mealName, foods);
        List<Meal_Food> meal_foods = new ArrayList<>();

        foods.forEach( food -> {
            meal_foods.add(new Meal_Food(food.getId(), mealName));
        });


        this.mealRepo.insert(meal);
        return this.mealFoodRepo.insert(meal_foods);
    }

    public void insertAll(List<Meal> t) {
        repo.insert(t);
    }

    public void update(Meal meal, String mealName) {
        final List<Food> foods = new ArrayList<>(checkedNutrients.values());
        Meal updatedMeal = new Meal(mealName, foods);
        updatedMeal.setId(meal.getId());
        List<Meal_Food> meal_foods = new ArrayList<>();

        foods.forEach( food -> {
            // TODO Check primary key make good composite key and set values accordingly otherwise this wont work
            meal_foods.add(new Meal_Food(food.getId(), mealName));
        });

        this.mealRepo.update(updatedMeal);

        this.mealFoodRepo.update(meal_foods);
    }

    public void updateAll(List<Meal> t) {
        repo.update(t);
    }

    public void delete(Meal t) {
        repo.delete(t);
        mealFoodRepo.delete(Meal_Food.class, t.getName());
        dayMealRepo.delete(Day_Meal.class, t.getId());
    }

    public void deleteFood(Food t) {
        mealFoodRepo.delete(Meal_Food.class, t.getId());
    }

    public void deleteAll(Meal t) {
        repo.delete(t);
    }
}
