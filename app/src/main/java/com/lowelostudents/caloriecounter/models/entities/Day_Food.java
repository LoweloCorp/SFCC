package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Entity;

import lombok.Data;

// ONE TO MANY THANKS TO AGGREGATIONS DELETE CASCADE, RELATION CLASSES OBSOLETE
// TODO because of the fact that I create Food Aggregation always, foodID and DayID composition is always unique
@Entity(primaryKeys = {"dayId", "id"})
@Data
public class Day_Food {
    private long id;
    private int dayId;

    public Day_Food(long id, int dayId) {
        this.id = id;
        this.dayId = dayId;
    }
}
