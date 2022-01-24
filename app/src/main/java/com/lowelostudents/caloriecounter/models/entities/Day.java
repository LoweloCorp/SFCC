package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lombok.Data;

@Entity
@Data
public class Day {
    @PrimaryKey
    private int dayId;
    private String name;

    public Day() {
        Calendar cal = Calendar.getInstance();
        this.dayId = cal.get(Calendar.DATE);
        this.name = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }

    @Dao
    public interface DayDao extends CRUDDao<Day> {
        @Query("SELECT * FROM Day WHERE dayId = (SELECT MAX(dayId) FROM Day)")
        Day getLatest();

        @Query("DELETE FROM Day WHERE dayId = :dayId")
        void deleteById(int dayId);

        @Query("SELECT * FROM Day")
        LiveData<List<Day>> getAllObservable();

        @Query("SELECT * FROM Day WHERE dayId = :date")
        LiveData<Day> getObservableByDate(int date);
    }
}
