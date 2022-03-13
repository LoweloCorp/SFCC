package com.lowelostudents.caloriecounter.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Index;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import lombok.Data;

// TODO other primary key, allow duplicate
@Entity(primaryKeys = {"id", "name"}, indices = {
        @Index(value = "id", unique = true),
        @Index(value = "name", unique = true)
})

@Data
public class Meal_Food {
    private long id;
    @NonNull
    private String name;

    public Meal_Food(long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }
}
