package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Locale;

import lombok.Data;

@Entity
@Data
public class Day {
    @PrimaryKey
    private int id;
    private String name;

    public Day() {
        Calendar cal = Calendar.getInstance();
        this.id = cal.get(Calendar.DATE);
        this.name = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }
}
