package com.lowelostudents.caloriecounter.models.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public abstract class DayDao extends CRUDDao<Day> {
    @Query("SELECT * FROM Day WHERE id = (SELECT MAX(id) FROM Day)")
    public abstract Day getLatest();

    @Query("SELECT * FROM Day")
    @Transaction
    public abstract List<Day_Food_Relation> getFoodsPerDay();

    @Query("SELECT * FROM Day WHERE id = :date")
    @Transaction
    public abstract Day_Food_Relation getFoodByDate(int date);

    @Query("SELECT * FROM Day")
    public abstract LiveData<List<Day>> getAllObservable();

    @Query("SELECT * FROM Day WHERE id = :date")
    public abstract LiveData<Day> getObservableByDate(int date);

    @Transaction
    @Query("SELECT * FROM Day WHERE Day.id = :id")
    public abstract Observable<Day_Food_Relation> getObservableFoodByDate(long id);

    @Transaction
    @Query("SELECT * FROM Day")
    public abstract LiveData<List<Day_Food_Relation>> getObservableFoodsPerDate();
//
//    @Transaction
//    @Query("SELECT * FROM Meal INNER JOIN Day_Meal ON Meal.id = Day_Meal.id WHERE Day_Meal.dayId = :date")
//    public abstract LiveData<List<Meal>> getObservableMealByDate(int date);
}
