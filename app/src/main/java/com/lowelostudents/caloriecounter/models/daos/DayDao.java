package com.lowelostudents.caloriecounter.models.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.lowelostudents.caloriecounter.models.Day;

import java.util.List;

@Dao
public interface DayDao {
    @Insert
    void insertAll(Day... days);

    @Query("SELECT * FROM Day")
    List<Day> getAll();

    @Query("SELECT * FROM Day WHERE dayId = :date")
    Day getOne(int date);

    @Update
    void updateOne(Day day);

    @Update
    void updateAll(Day... days);

    @Delete
    void deleteOne(Day day);

    @Delete
    void deleteAll(Day... days);
}
