package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.models.CrudDao;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import java.util.List;

import lombok.Getter;

public class DayFoodRepo extends CrudRepository<Day_Food> {
    @Getter
    private final LiveData<List<Day_Food_Relation>> day_foods;

    DayFoodRepo(Context context) {
        super(context);
        setCrudDao(getAppdb().day_foodDao());
        final Day_Food.Day_FoodDao day_foodDao = (Day_Food.Day_FoodDao) getCrudDao();
        day_foods = day_foodDao.getAllObservable();
    }
}
