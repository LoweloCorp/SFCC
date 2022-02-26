package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;
import com.lowelostudents.caloriecounter.models.relations.Day_Meal_Relation;

import java.util.List;

import lombok.Getter;

public class DayRepo extends CRUDRepository<Day> {
    private final Day.DayDao dayDao;
    @Getter
    private final LiveData<List<Day_Food_Relation>> day_foods;
    @Getter
    private final LiveData<List<Day_Meal_Relation>> day_meals;

    public DayRepo(Context context) {
        super(context);
        dayDao = getAppdb().dayDao();
        super.setCrudDao(dayDao);
        day_foods = dayDao.getObservableFoodsPerDate();
        day_meals = dayDao.getObservableMealsPerDate();
    }
}
