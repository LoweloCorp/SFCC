package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.interfaces.FoodDao;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class FoodRepo extends CRUDRepository<Food> {
    @Getter
    private final Observable<List<Food>> foods;
    @Getter
    private final Observable<List<Food>> meals;
    @Getter
    private final Observable<List<Food>> mealsAndFoods;

    private final FoodDao foodDao;

    public FoodRepo(Context context) {
        super(context);
        super.setCrudDao(getAppdb().foodDao());
        this.foodDao = (FoodDao) super.getCrudDao();
        foods = foodDao.getAllObservable(AggregationType.FOOD);
        meals = foodDao.getAllObservable(AggregationType.MEAL);
        mealsAndFoods = foodDao.getAllObservable();
    }

    public void insertForDay(List<Food> foods, long id) {
        foods.forEach( food -> food.setDayId(id));

        executor.execute(() -> this.getCrudDao().insert(foods));
    }

    public void insertForDay(Food food, long id) {
        food.setDayId(id);

        executor.execute(() -> this.getCrudDao().insert(food));
    }

    public void insertForMeal(Food meal, List<Food> foods) {
        foods.forEach( food -> food.setMealId(meal.getId()));

        executor.execute(() -> {
            this.getCrudDao().insert(meal);
            this.getCrudDao().insert(foods);
        });
    }

    public void insertForMeal(Food meal, Food food) {
        food.setMealId(meal.getId());

        executor.execute(() -> this.getCrudDao().insert(food));
    }


    public Observable<Meal_Food_Relation> getMealFoods(UUID id) {
        return this.foodDao.getObservableFoodByMeal(id);
    }
}
