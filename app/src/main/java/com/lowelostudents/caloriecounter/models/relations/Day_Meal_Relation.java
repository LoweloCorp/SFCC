package com.lowelostudents.caloriecounter.models.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.models.entities.Day_Meal;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;

import java.util.List;

import lombok.Data;

@Data
public class Day_Meal_Relation {
    @Embedded
    private Day day;
    @Relation(
            parentColumn = "dayId",
            entityColumn = "id",
            associateBy = @Junction(Day_Meal.class)
    )

    private List<Meal> meals;
}