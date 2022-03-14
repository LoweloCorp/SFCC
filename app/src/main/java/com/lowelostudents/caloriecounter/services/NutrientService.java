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
        nutrients.setCarbsCal(nutrients.getCarbsGram() * 4);
        nutrients.setProteinCal(nutrients.getProteinGram() * 4);
        nutrients.setFatCal(nutrients.getFatGram() * 9);
        if (nutrients.getCalPerPortion() == 0)
            nutrients.setCalPerPortion(nutrients.getFatCal() + nutrients.getProteinCal() + nutrients.getCarbsCal());
        nutrients.setCalPerGram((double) nutrients.getCalPerPortion() / nutrients.getPortionSize());
        nutrients.setCalTotal((int) Math.round(nutrients.getCalPerGram() * nutrients.getGramTotal()));
    }

    public <T extends Nutrients, R extends Nutrients> void combineNutrients(T nutrients, List<R> nutrientList) {
        nutrientList.forEach(food -> {
            nutrients.setFatGram(nutrients.getFatGram() + food.getFatGram());
            nutrients.setProteinGram(nutrients.getProteinGram() + food.getProteinGram());
            nutrients.setCarbsGram(nutrients.getCarbsGram() + food.getCarbsGram());
            nutrients.setCarbsCal(nutrients.getCarbsGram() + food.getCarbsCal());
            nutrients.setProteinCal(nutrients.getProteinGram() + food.getProteinGram());
            nutrients.setFatCal(nutrients.getFatGram() + food.getFatGram());
            nutrients.setPortionSize(nutrients.getPortionSize() + food.getPortionSize());
            nutrients.setCalPerPortion(nutrients.getCalPerPortion() + food.getCalPerPortion());
            nutrients.setCalPerGram(nutrients.getCalPerGram() + food.getCalPerGram());
            nutrients.setCalTotal(nutrients.getCalTotal() + food.getCalTotal());
            nutrients.setGramTotal(nutrients.getGramTotal() + food.getGramTotal());
        });
    }

    public <R extends Nutrients> Nutrients combineNutrients(List<R> nutrientList) {
        Nutrients nutrients = new Nutrients();

        nutrientList.forEach(food -> {
            nutrients.setFatGram(nutrients.getFatGram() + food.getFatGram());
            nutrients.setProteinGram(nutrients.getProteinGram() + food.getProteinGram());
            nutrients.setCarbsGram(nutrients.getCarbsGram() + food.getCarbsGram());
            nutrients.setCarbsCal(nutrients.getCarbsGram() + food.getCarbsCal());
            nutrients.setProteinCal(nutrients.getProteinGram() + food.getProteinGram());
            nutrients.setFatCal(nutrients.getFatGram() + food.getFatGram());
            nutrients.setPortionSize(nutrients.getPortionSize() + food.getPortionSize());
            nutrients.setCalPerPortion(nutrients.getCalPerPortion() + food.getCalPerPortion());
            nutrients.setCalPerGram(nutrients.getCalPerGram() + food.getCalPerGram());
            nutrients.setCalTotal(nutrients.getCalTotal() + food.getCalTotal());
            nutrients.setGramTotal(nutrients.getGramTotal() + food.getGramTotal());
        });

        return nutrients;
    }
}
