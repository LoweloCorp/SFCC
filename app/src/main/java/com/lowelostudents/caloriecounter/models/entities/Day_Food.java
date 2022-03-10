package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import lombok.Data;


// TODO day Meal relation and Pair MediatorLiveData
@Entity(primaryKeys = {"dayId", "id"},
        indices = {
                @Index(value = "dayId", unique = true),
                @Index(value = "id", unique = true)
        })
@Data
// TODO allow duplicates
public class Day_Food {
    private long id;
    private int dayId;

    public Day_Food(long id, int dayId) {
        this.id = id;
        this.dayId = dayId;
    }

    @Dao
    public abstract static class Day_FoodDao extends CRUDDao<Day_Food> {
    }
}
