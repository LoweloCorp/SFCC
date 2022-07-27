package com.lowelostudents.caloriecounter.models.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public abstract class FoodDao extends CRUDDao<Food> {
    @Query("SELECT * FROM Food WHERE isAggregation = 0")
    public abstract LiveData<List<Food>> getAllObservable();

    @Query("SELECT * FROM Food WHERE isAggregation = 0")
    public abstract Observable<List<Food>> getAllObservableRX();

    @Query("SELECT * FROM Food Where id = :id")
    public abstract LiveData<Food> getObservable(long id);
}
