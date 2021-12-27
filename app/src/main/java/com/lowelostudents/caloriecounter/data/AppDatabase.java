package com.lowelostudents.caloriecounter.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lowelostudents.caloriecounter.models.Day;
import com.lowelostudents.caloriecounter.models.daos.DayDao;
import com.lowelostudents.caloriecounter.models.Day_Food;
import com.lowelostudents.caloriecounter.models.daos.Day_FoodDao;
import com.lowelostudents.caloriecounter.models.Food;
import com.lowelostudents.caloriecounter.models.daos.FoodDao;
import com.lowelostudents.caloriecounter.models.Meal;
import com.lowelostudents.caloriecounter.models.daos.MealDao;
import com.lowelostudents.caloriecounter.models.Meal_Food;
import com.lowelostudents.caloriecounter.models.daos.Meal_FoodDao;

@Database(entities = {Day.class, Food.class, Day_Food.class, Meal.class, Meal_Food.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "SFCCDatabase";
    private static AppDatabase dbInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (dbInstance == null)
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        return dbInstance;
    }

    public static synchronized AppDatabase getInMemoryInstance(Context context) {
        if (dbInstance == null)
            dbInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    .fallbackToDestructiveMigration()
                    .build();

        return dbInstance;
    }

    public abstract DayDao dayDao();
    public abstract FoodDao foodDao();
    public abstract MealDao mealDao();
    public abstract Day_FoodDao day_foodDao();
    public abstract Meal_FoodDao meal_foodDao();
}
