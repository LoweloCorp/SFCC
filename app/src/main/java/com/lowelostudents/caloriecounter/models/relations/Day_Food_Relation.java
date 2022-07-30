package com.lowelostudents.caloriecounter.models.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.List;

import lombok.Data;

@Data
public class Day_Food_Relation {
    @Embedded
    private Day day;
    @Relation(
            parentColumn = "id",
            entityColumn = "dayId"
    )
    private List<Food> foods;
}