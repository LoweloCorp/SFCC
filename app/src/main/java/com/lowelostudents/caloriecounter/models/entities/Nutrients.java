package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

// TODO add Protein etc
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Nutrients implements Serializable {
    @Ignore
    private static final long serialVersionUID = 1L;
    @EqualsAndHashCode.Include
    protected String name;
    protected int calTotal = 0;
    protected int gramTotal = 0;
    protected int carbsGram = 0, carbsCal = 0;
    protected int proteinGram = 0, proteinCal = 0;
    protected int fatGram = 0, fatCal = 0;
    protected int portionSize = 0;
    protected int calPerPortion = 0;
    protected double calPerGram = 0;
}
