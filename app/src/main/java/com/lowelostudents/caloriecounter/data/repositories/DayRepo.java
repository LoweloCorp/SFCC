package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;
import com.lowelostudents.caloriecounter.models.relations.Day_Meal_Relation;

import java.util.Calendar;
import java.util.List;

import lombok.Getter;

public class DayRepo extends CRUDRepository<Day> {
    private final Day.DayDao dayDao;
    @Getter
    private final LiveData<List<Food>> day_foods;
    @Getter
    private final LiveData<List<Meal>> day_meals;

    public DayRepo(Context context) {
        super(context);
        dayDao = getAppdb().dayDao();
        super.setCrudDao(dayDao);
        Calendar cal = Calendar.getInstance();
        day_foods = dayDao.getObservableFoodByDate(cal.get(Calendar.DATE));
        day_meals = dayDao.getObservableMealByDate(cal.get(Calendar.DATE));
    }
}
