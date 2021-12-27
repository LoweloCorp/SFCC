package com.lowelostudents.caloriecounter.models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class Day_Food_Relation {
    @Embedded public Day day;
    @Relation(
            parentColumn = "dayId",
            entityColumn = "foodId",
            associateBy = @Junction(Day_Food.class)
    )
    public List<Food> foods;
}
