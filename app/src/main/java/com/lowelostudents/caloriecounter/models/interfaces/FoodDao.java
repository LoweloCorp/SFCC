package com.lowelostudents.caloriecounter.models.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.List;

@Dao
public abstract class FoodDao extends CRUDDao<Food> {
    @Query("SELECT * FROM Food WHERE isAggregation = 0")
    public abstract LiveData<List<Food>> getAllObservable();

    @Query("SELECT * FROM Food Where id = :id")
    public abstract LiveData<Food> getObservable(long id);
}
