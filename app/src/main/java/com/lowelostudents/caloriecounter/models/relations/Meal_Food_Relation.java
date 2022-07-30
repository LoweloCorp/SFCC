package com.lowelostudents.caloriecounter.models.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.List;

import lombok.Data;

// TODO only use primary keys
@Data
public class Meal_Food_Relation {
    @Relation(
            parentColumn = "id",
            entityColumn = "mealId"
            )

    List<Food> foods;

    @Embedded
    private Food meal;
}