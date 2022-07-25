package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.services.NutrientService;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;


//TODO add constructor with total calories parameter
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Food extends Nutrients implements Serializable {
    @Ignore
    private static final long serialVersionUID = 1L;
    @PrimaryKey(autoGenerate = true)
    @EqualsAndHashCode.Include
    protected long id;
    @Ignore
    private transient NutrientService nutrientService = NutrientService.getInstance();

    private boolean isAggregation = false;

    // TODO NEXT gramTotal Nullable
    public Food(String name, double carbsGramPortion, double proteinGramPortion, double fatGramPortion, double portionSize, double gramTotal) {
        this.name = name;
        this.carbsGram = carbsGramPortion;
        this.proteinGram = proteinGramPortion;
        this.fatGram = fatGramPortion;

        this.portionSize = portionSize;
        this.gramTotal = gramTotal;
    }

    // TODO Deprercate enforce macro nutrients
    public Food(String name, double gramTotal, double calPerPortion, double portionSize) {
        this.name = name;
        this.gramTotal = gramTotal;
        this.calPerPortion = calPerPortion;
        this.portionSize = portionSize;
    }

    public Food(String name, List<Food> foodList, double multiplier) {
        this.isAggregation = true;
        this.name = name;
        nutrientService.combineNutrients(this, foodList);
        nutrientService.applyMultiplier(this, multiplier);
    }

    @Ignore
    public Food() {

    }
}
