package com.lowelostudents.caloriecounter.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
public class Meal {
    @PrimaryKey(autoGenerate = true)
    private long mealId;
    private String mealName;

    @Ignore
    public Meal(){

    }

    public Meal(String mealName){
        this.mealName = mealName;
    }
}
