package com.lowelostudents.caloriecounter.models.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Meal_Food;

import java.util.List;

import lombok.Data;

// TODO generify
@Data
public class Meal_Food_Relation {
    @Relation(
            parentColumn = "id",
            entityColumn = "name",
            associateBy = @Junction(Meal_Food.class)
            )

    List<Food> foods;

    @Embedded
    private Meal meal;
}