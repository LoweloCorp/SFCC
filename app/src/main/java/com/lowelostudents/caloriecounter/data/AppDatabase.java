package com.lowelostudents.caloriecounter.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.models.interfaces.DayDao;
import com.lowelostudents.caloriecounter.models.interfaces.FoodDao;
import com.lowelostudents.caloriecounter.models.interfaces.UserDao;

@Database(entities = {Day.class, Food.class, User.class}, version = 2)
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
    public abstract UserDao userDao();
}
