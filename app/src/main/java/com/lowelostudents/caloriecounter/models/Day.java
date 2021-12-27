package com.lowelostudents.caloriecounter.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Day {
    @PrimaryKey
    @EqualsAndHashCode.Include
    private int dayId;
    private String name;

    public Day() {
        Calendar cal = Calendar.getInstance();
        this.dayId = cal.get(Calendar.DATE);
        this.name = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }
}
