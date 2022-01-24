package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.List;

import lombok.Getter;

public class FoodRepo extends CrudRepository<Food> {
    @Getter
    private final LiveData<List<Food>> food;

    public FoodRepo(Context context) {
        super(context);
        setCrudDao(getAppdb().foodDao());
        final Food.FoodDao foodDao = (Food.FoodDao) getCrudDao();
        food = foodDao.getAllObservable();
    }
}
