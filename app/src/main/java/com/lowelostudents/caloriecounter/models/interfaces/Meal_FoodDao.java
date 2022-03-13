package com.lowelostudents.caloriecounter.models.interfaces;


import androidx.room.Dao;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.Meal_Food;

@Dao
public abstract class Meal_FoodDao extends CRUDDao<Meal_Food> {
}