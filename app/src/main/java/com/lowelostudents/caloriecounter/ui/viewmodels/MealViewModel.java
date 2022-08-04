package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.lowelostudents.caloriecounter.data.repositories.FoodRepo;
import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class MealViewModel extends AndroidViewModel {
    @Getter
    private final Observable<List<Food>> meals;
    private final FoodRepo foodRepo;
    public final HashMap<UUID, Food> checkedFoods = new HashMap<>();

    public MealViewModel(Application context) {
        super(context);
        this.foodRepo = new FoodRepo(context.getApplicationContext());
        this.meals = this.foodRepo.getMeals();
    }

    public Observable<Meal_Food_Relation> getMealFoods(UUID id) {
        return this.foodRepo.getMealFoods(id);
    }

    // TODO put in DayViewModel
    public void addToDay(Food food) {
        Calendar cal = Calendar.getInstance();

        this.foodRepo.insertForDay(food, cal.get(Calendar.DATE));
    }

    // TODO rework & put in DayViewModel
    public void removeFromDay(Food meal) {
        this.foodRepo.delete(meal);
//        meal.setMealId(null);
//        this.foodRepo.insert(meal);
    }

    public void insert (String mealName) {
        final List<Food> foods = new ArrayList<>(checkedFoods.values());
        Food meal = new Food(mealName, foods, 1, AggregationType.MEAL);
        meal.setAggregation(false);

        this.foodRepo.insertForMeal(meal, foods);
    }

    // TODO rework
    public void update(Food meal) {
        this.foodRepo.delete(meal);

        this.insert(meal.getName());
    }

    public void delete(Food t) {
        this.foodRepo.delete(t);
    }
}
