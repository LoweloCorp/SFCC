package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Nutrients {
    @PrimaryKey(autoGenerate = true)
    @EqualsAndHashCode.Include
    protected long id;
    @EqualsAndHashCode.Include
    protected String name;
    protected int calTotal;
    protected int gramTotal;
}
