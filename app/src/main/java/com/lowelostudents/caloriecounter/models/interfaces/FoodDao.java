package com.lowelostudents.caloriecounter.models.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;

@Dao
public abstract class FoodDao extends CRUDDao<Food> {
    @Query("SELECT * FROM Food WHERE isAggregation = 0 AND aggregationType = :aggregationType")
    public abstract Observable<List<Food>> getAllObservable(AggregationType aggregationType);

    @Query("SELECT * FROM Food WHERE isAggregation = 0")
    public abstract Observable<List<Food>> getAllObservable();

    @Query("SELECT * FROM Food Where id = :id")
    public abstract LiveData<Food> getObservable(long id);

    @Transaction
    @Query("SELECT * FROM Food WHERE Food.id = :uuid")
    public abstract Observable<Meal_Food_Relation> getObservableFoodByMeal(UUID uuid);
}
