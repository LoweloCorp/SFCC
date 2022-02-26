package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;

public class DayFoodRepo extends CRUDRepository<Day_Food> {
    public DayFoodRepo(Context context) {
        super(context);
        super.setCrudDao(getAppdb().day_foodDao());
    }
}
