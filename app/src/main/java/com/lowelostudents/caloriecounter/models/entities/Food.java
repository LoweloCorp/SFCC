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

    public Food(String name, int carbsGramPortion, int proteinGramPortion, int fatGramPortion, int portionSize, int gramTotal) {
        this.name = name;
        this.carbsGram = carbsGramPortion;
        this.proteinGram = proteinGramPortion;
        this.fatGram = fatGramPortion;
        this.portionSize = portionSize;
        this.gramTotal = gramTotal;
        nutrientService.calculateNutrients(this);
    }

    public Food(String name, int gramTotal, int calPerPortion, int portionSize) {
        this.name = name;
        this.gramTotal = gramTotal;
        this.calPerPortion = calPerPortion;
        this.portionSize = portionSize;
        nutrientService.calculateNutrients(this);
    }

    @Ignore
    public Food() {

    }
}
