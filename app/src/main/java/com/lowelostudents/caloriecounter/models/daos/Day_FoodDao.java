package com.lowelostudents.caloriecounter.models.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.Day_Food;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import java.util.List;

@Dao
public interface Day_FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Day_Food... day_foods);

    @Transaction
    @Query("SELECT * FROM Day")
    List<Day_Food_Relation> getAll();

    @Transaction
    @Query("SELECT * FROM Day WHERE dayId = :date")
    Day_Food_Relation getOne(int date);
}
