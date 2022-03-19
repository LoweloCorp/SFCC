package com.lowelostudents.caloriecounter.services;

import android.util.Log;

import androidx.annotation.Nullable;

import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;

import java.util.List;

//TODO calculation water or any other thing with mass but almost no calories
// TODO generify and overload calcCalories with one that returns Hashmap
public class NutrientService {
    private static NutrientService nutrientService;

    public static synchronized NutrientService getInstance() {
        if (nutrientService == null) nutrientService = new NutrientService();

        return nutrientService;
    }

    // TODO universal formulas

    public <T extends Nutrients> void calculateNutrients(T nutrients) {
        int carbsCalPortion, proteinCalPortion, fatCalPortion;
        double carbsCalGram, proteinCalGram, fatCalGram;

        carbsCalPortion = nutrients.getCarbsGram() * 4;
        proteinCalPortion = nutrients.getProteinGram() * 4;
        fatCalPortion = nutrients.getFatGram() * 9;
        if (nutrients.getCalPerPortion() == 0) {
            // 8 + 4 + 18 = 30
            nutrients.setCalPerPortion(carbsCalPortion + proteinCalPortion + fatCalPortion);
            // 2.666
            carbsCalGram = (double) carbsCalPortion / nutrients.getPortionSize();
            // 1.333
            proteinCalGram = (double) proteinCalPortion / nutrients.getPortionSize();
            // 6
            fatCalGram = (double) fatCalPortion / nutrients.getPortionSize();
            nutrients.setCarbsCal((int) Math.round(carbsCalGram * nutrients.getGramTotal()));
            nutrients.setProteinCal((int) Math.round(proteinCalGram * nutrients.getGramTotal()));
            // 36
            nutrients.setFatCal((int) Math.round(fatCalGram * nutrients.getGramTotal()));
        }
        nutrients.setCalPerGram((double) nutrients.getCalPerPortion() / nutrients.getPortionSize());
        nutrients.setCalTotal((int) Math.round(nutrients.getCalPerGram() * nutrients.getGramTotal()));
    }

    public <T extends Nutrients, R extends Nutrients> void combineNutrients(T nutrients, List<R> nutrientList) {
        nutrientList.forEach(food -> {
            nutrients.setFatGram(nutrients.getFatGram() + food.getFatGram());
            nutrients.setProteinGram(nutrients.getProteinGram() + food.getProteinGram());
            nutrients.setCarbsGram(nutrients.getCarbsGram() + food.getCarbsGram());

            nutrients.setCarbsCal(nutrients.getCarbsCal() + food.getCarbsCal());
            nutrients.setProteinCal(nutrients.getProteinCal() + food.getProteinCal());
            nutrients.setFatCal(nutrients.getFatCal() + food.getFatCal());

            nutrients.setPortionSize(nutrients.getPortionSize() + food.getPortionSize());

            nutrients.setCalPerPortion(nutrients.getCalPerPortion() + food.getCalPerPortion());

            nutrients.setCalPerGram(nutrients.getCalPerGram() + food.getCalPerGram());

            nutrients.setCalTotal(nutrients.getCalTotal() + food.getCalTotal());

            nutrients.setGramTotal(nutrients.getGramTotal() + food.getGramTotal());
        });
    }

    public <R extends Nutrients> Nutrients combineNutrients(List<R> nutrientList) {
        Nutrients nutrients = new Nutrients();

        this.combineNutrients(nutrients, nutrientList);

        return nutrients;
    }
}
