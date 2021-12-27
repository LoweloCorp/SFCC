package com.lowelostudents.caloriecounter.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Food {
    @PrimaryKey(autoGenerate = true)
    @EqualsAndHashCode.Include
    private long foodId;
    @EqualsAndHashCode.Include
    private String name;
    private int carbsGram, carbsCal;
    private int proteinGram, proteinCal;
    private int fatGram, fatCal;
    private int portionSize;
    private int calPerPortion;
    private int calPerGram;
    private int gramTotal;
    private int calTotal;

    public Food(String name, int carbsGramPortion, int proteinGramPortion, int fatGramPortion, int gramTotal) {
        this.name = name;
        this.carbsGram = carbsGramPortion;
        this.proteinGram=proteinGramPortion;
        this.fatGram = fatGramPortion;
        this.gramTotal = gramTotal;
    }

    public Food(String name, int gramTotal, int calPerPortion, int portionSize) {
        this.name = name;
        this.gramTotal = gramTotal;
        this.calPerPortion = calPerPortion;
        this.portionSize = portionSize;
    }
}
