package com.lowelostudents.caloriecounter.models.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public abstract class MealDao extends CRUDDao<Meal> {
    @Query("SELECT * FROM Meal")
    @Transaction
    public abstract List<Meal_Food_Relation> getFoodPerMeal();

    @Query("SELECT * FROM Meal WHERE id = :id")
    @Transaction
    public abstract List<Meal_Food_Relation> getFoodPerMeal(long id);

    @Query("SELECT * FROM Meal")
    public abstract LiveData<List<Meal>> getAllObservable();

    @Query("SELECT * FROM Meal WHERE id = :id")
    public abstract LiveData<Meal> getObservable(long id);

    @Transaction
    @Query("SELECT * FROM Meal")
    public abstract LiveData<List<Meal_Food_Relation>> getAllObservableTransaction();

    @Transaction
    @Query("SELECT * FROM Meal WHERE id = :id")
    public abstract LiveData<Meal_Food_Relation> getObservableTransaction(long id);

    @Transaction
    @Query("SELECT * FROM Food INNER JOIN Meal_Food ON Food.id = Meal_Food.id WHERE Meal_Food.name = :mealName")
    public abstract LiveData<List<Food>> getObservableFoodByMeal(String mealName);

    @Transaction
    @Query("SELECT * FROM Food INNER JOIN Meal_Food ON Food.id = Meal_Food.id WHERE Meal_Food.name = :mealName")
    public abstract Observable<List<Food>> getObservableFoodByMealRX(String mealName);
}
