package com.lowelostudents.caloriecounter.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lowelostudents.caloriecounter.models.Day;
import com.lowelostudents.caloriecounter.models.DayDao;
import com.lowelostudents.caloriecounter.models.Day_Food;
import com.lowelostudents.caloriecounter.models.Food;
import com.lowelostudents.caloriecounter.models.FoodDao;
import com.lowelostudents.caloriecounter.services.TypeConverterService;

@Database(entities = {Day.class, Food.class, Day_Food.class}, version = 1)
@TypeConverters({TypeConverterService.class})
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
}
