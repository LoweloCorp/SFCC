package com.lowelostudents.caloriecounter.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DayDao {
    @Insert
    void insertAll(Day... days);

    @Transaction
    @Query("SELECT * FROM Day")
    List<Day_Food_Relation> getDays();

    @Transaction
    @Query("SELECT * FROM Day WHERE dayId = :date")
    Day_Food_Relation getDay(int date);

    @Update
    void updateDay(Day day);

    @Update
    void updateDays(List<Day> days);

    @Delete
    void deleteDay(Day day);
}
