package com.lowelostudents.caloriecounter.models.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.List;

import lombok.Data;

@Data
public class Day_Food_Relation {
    @Embedded
    private Day day;
    @Relation(
            parentColumn = "dayId",
            entityColumn = "foodId",
            associateBy = @Junction(Day_Food.class)
    )
    private List<Food> foods;
}