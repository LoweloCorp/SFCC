package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.interfaces.FoodDao;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class FoodRepo extends CRUDRepository<Food> {
    @Getter
    private final LiveData<List<Food>> foods;
    @Getter
    private final Observable<List<Food>> foodsRX;

    public FoodRepo(Context context) {
        super(context);
        super.setCrudDao(getAppdb().foodDao());
        final FoodDao foodDao = (FoodDao) super.getCrudDao();
        foods = foodDao.getAllObservable();
        foodsRX = foodDao.getAllObservableRX();
    }
}
