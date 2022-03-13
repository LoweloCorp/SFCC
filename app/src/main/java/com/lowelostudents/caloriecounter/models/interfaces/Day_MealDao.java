package com.lowelostudents.caloriecounter.models.interfaces;


import androidx.room.Dao;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Day_Meal;

@Dao
public abstract class Day_MealDao extends CRUDDao<Day_Meal> {
}
