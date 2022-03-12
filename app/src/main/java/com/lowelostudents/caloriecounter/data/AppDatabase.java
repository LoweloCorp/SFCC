package com.lowelostudents.caloriecounter.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.models.entities.Day_Meal;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Meal_Food;
import com.lowelostudents.caloriecounter.models.entities.User;

@Database(entities = {Day.class, Food.class, Day_Food.class, Meal.class, Meal_Food.class, Day_Meal.class, User.class}, version = 1)
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

    public abstract Day.DayDao dayDao();

    public abstract Food.FoodDao foodDao();

    public abstract Meal.MealDao mealDao();

    public abstract Day_Food.Day_FoodDao day_foodDao();

    public abstract Meal_Food.Meal_FoodDao meal_foodDao();

    public abstract Day_Meal.Day_MealDao day_mealDao();

    public abstract User.UserDao userDao();
}
