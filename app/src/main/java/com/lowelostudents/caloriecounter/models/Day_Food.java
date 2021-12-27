package com.lowelostudents.caloriecounter.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.Calendar;
import java.util.Date;

import lombok.Data;

@Entity(primaryKeys = {"dayId", "foodId"})
@Data
public class Day_Food {
    private long foodId;
    private int dayId;
}
