package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.interfaces.MealDao;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class MealRepo extends CRUDRepository<Meal> {
    @Getter
    private final LiveData<List<Meal>> meals;
    @Getter
    private final LiveData<List<Meal_Food_Relation>> meal_foods;
    @Getter
    private final MealDao mealDao;

    public MealRepo(Context context) {
        super(context);
        this.mealDao = getAppdb().mealDao();
        super.setCrudDao(this.mealDao);
        final MealDao mealDao = (MealDao) super.getCrudDao();
        meals = mealDao.getAllObservable();
        meal_foods = mealDao.getAllObservableTransaction();
    }

    public Observable<List<Food>> getMealFoods(String mealName) {
        return this.mealDao.getObservableFoodByMealRX(mealName);
    }
}
