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
    long[] insertAll(Day... days);

    @Query("SELECT * FROM Day")
    List<Day> getAll();

    @Query("SELECT * FROM Day WHERE dayId = :date")
    Day getOne(int date);

    @Query("SELECT * FROM Day WHERE dayId = (SELECT MAX(dayId) FROM Day)")
    Day getLatest();

    @Update
    void updateAll(Day... days);

    @Delete
    void deleteAll(Day... days);

    @Query("DELETE FROM Day WHERE dayId = :dayId")
    void deleteSpecific(int dayId);

}
