package com.lowelostudents.caloriecounter.models.interfaces;

import androidx.room.Dao;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;

@Dao
public abstract class Day_FoodDao extends CRUDDao<Day_Food> {
}
