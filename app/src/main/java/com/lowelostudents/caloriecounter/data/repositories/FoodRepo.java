package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import java.util.List;

import lombok.Getter;

public class FoodRepo extends CRUDRepository<Food, Day_Food_Relation> {
    @Getter
    private final LiveData<List<Food>> foods;

    public FoodRepo(Context context) {
        super(context);
        super.setCrudDao(getAppdb().foodDao());
        final Food.FoodDao foodDao = (Food.FoodDao) super.getCrudDao();
        foods = foodDao.getAllObservable();
    }
}
