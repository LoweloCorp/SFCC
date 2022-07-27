package com.lowelostudents.caloriecounter.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import lombok.Data;

@Entity(indices = {
        @Index(value = "id", unique = false),
        @Index(value = "name", unique = false)
})

@Data
public class Meal_Food {
    @PrimaryKey(autoGenerate = true)
    private long primaryKey;
    private long id;
    //FIXME VERY IMPORTANT apparently gets confused by name USE PRIMARY KEYS
    @NonNull
    private String name;

    public Meal_Food(long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
