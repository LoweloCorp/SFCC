package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.CrudDao;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import java.util.List;

import lombok.Data;

@Entity(primaryKeys = {"dayId", "foodId"})
@Data
public class Day_Food {
    private long foodId;
    private int dayId;

    @Dao
    public interface Day_FoodDao extends CrudDao<Day_Food> {
        @Transaction
        @Query("SELECT * FROM Day")
        List<Day_Food_Relation> getAll();

        @Transaction
        @Query("SELECT * FROM Day WHERE dayId = :date")
        Day_Food_Relation getByDate(int date);

        @Transaction
        @Query("SELECT * FROM Day WHERE dayId >= :date")
        List<Day_Food_Relation> getByDateUntil(int date);

        @Transaction
        @Query("SELECT * FROM Day WHERE dayId = :date")
        LiveData<Day_Food_Relation> getObservableByDate(int date);

        @Transaction
        @Query("SELECT * FROM Day")
        LiveData<List<Day_Food_Relation>> getAllObservable();
    }
}
