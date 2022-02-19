package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.repositories.FoodRepo;
import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.List;

import lombok.Getter;

public class FoodViewModel extends AndroidViewModel {
    @Getter
    private final LiveData<List<Food>> foods;
    private final FoodRepo repo;

    public FoodViewModel(Application context) {
        super(context);
        repo = new FoodRepo(context.getApplicationContext());
        foods = repo.getFoods();
    }

    public Long insert(Food t) {
        return repo.insert(t);
    }

    public Long[] insertAll(List<Food> t) {
        return repo.insert(t);
    }

    public void update(Food t) {
        repo.update(t);
    }

    public void updateAll(List<Food> t) {
        repo.update(t);
    }

    public void delete(Food t) {
        repo.delete(t);
    }

    public void deleteAll(Food t) {
        repo.delete(t);
    }

    public <A extends Activity> void finish(A activity) {
        activity.finish();
    }
}