package com.lowelostudents.caloriecounter.models.interfaces;

import androidx.room.Dao;
import androidx.room.Query;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;

@Dao
public abstract class Day_FoodDao extends CRUDDao<Day_Food> {
//
//    @Query("DELETE FROM Day_Food WHERE id = :id")
//    public abstract void deleteById(long id);
}
