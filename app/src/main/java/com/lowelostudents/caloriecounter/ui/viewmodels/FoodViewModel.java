package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.lowelostudents.caloriecounter.data.repositories.FoodRepo;
import com.lowelostudents.caloriecounter.data.repositories.InsertCallback;
import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class FoodViewModel extends AndroidViewModel {
    @Getter
    private final Observable<List<Food>> foods;
    @Getter
    private final Observable<List<Food>> mealsAndFoods;

    private final FoodRepo repo;

    public FoodViewModel(Application context) {
        super(context);
        repo = new FoodRepo(context.getApplicationContext());
        foods = repo.getFoods();
        mealsAndFoods = repo.getMealsAndFoods();
    }

    public FoodRepo getRepo() {
        return this.repo;
    }

    public void insert(Food t) {
        repo.insert(t);
    }

    public void addToDay(Food food) {
        Calendar cal = Calendar.getInstance();

        repo.insertForDay(food, cal.get(Calendar.DATE));
    }

    // TODO rework additinoal constructor maybe similar to OBject.assign()
    public void removeFromDay(Food food) {
        this.repo.delete(food);
        ArrayList<Food> foodList = new ArrayList<>();
        foodList.add(food);
        this.repo.insert(new Food(food.getName(), foodList, 1, AggregationType.FOOD));
    }

    public void update(Food t) {
        repo.update(t);
    }

    public void delete(Food t) {
        repo.delete(t);
    }

    public <A extends Activity> void finish(A activity) {
        activity.finish();
    }
}