package com.lowelostudents.caloriecounter.services;

import android.util.Log;

import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;

import java.util.ArrayList;
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
        double carbsCalPortion, proteinCalPortion, fatCalPortion;
        double carbsCalGram, proteinCalGram, fatCalGram;

        carbsCalPortion = nutrients.getCarbsGram() * 4;
        proteinCalPortion = nutrients.getProteinGram() * 4;
        fatCalPortion = nutrients.getFatGram() * 9;

        if (nutrients.getCalPerPortion() == 0) {
            nutrients.setCalPerPortion(carbsCalPortion + proteinCalPortion + fatCalPortion);

            carbsCalGram = carbsCalPortion / nutrients.getPortionSize();
            proteinCalGram = proteinCalPortion / nutrients.getPortionSize();
            fatCalGram = fatCalPortion / nutrients.getPortionSize();

            nutrients.setCarbsCal(carbsCalGram * nutrients.getGramTotal());
            nutrients.setProteinCal(proteinCalGram * nutrients.getGramTotal());
            nutrients.setFatCal(fatCalGram * nutrients.getGramTotal());
        }

        nutrients.setCalPerGram(nutrients.getCalPerPortion() / nutrients.getPortionSize());

        nutrients.setCalTotal(nutrients.getCalPerGram() * nutrients.getGramTotal());
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

    public <T extends Nutrients> void applyMultiplier(T nutrients, double multiplier) {
        nutrients.setFatCal(nutrients.getFatCal() * multiplier);
        nutrients.setProteinCal(nutrients.getProteinCal() * multiplier);
        nutrients.setCarbsCal(nutrients.getCarbsCal() * multiplier);
        nutrients.setCalTotal(nutrients.getCalTotal() * multiplier);
        nutrients.setGramTotal(nutrients.getGramTotal() * multiplier);
    }

    public <R extends Nutrients> Nutrients combineNutrients(List<R> nutrientList) {
        Nutrients nutrients = new Nutrients();

        this.combineNutrients(nutrients, nutrientList);

        return nutrients;
    }

    public Food createFoodAggregation(Food data, double quantity, AggregationType aggregationType) {
        double multiplier = quantity / data.getCarbsCal();
        double necessaryFoodInstances = Math.ceil(multiplier);
        List<Food> foodList = new ArrayList<>();

        for (int i = 0; i < necessaryFoodInstances; i++) {
            foodList.add(data);
        }

        // TODO problem for createMeal
        Log.w("NAME", data.getName());
        return new Food(data.getName(), foodList, multiplier, aggregationType);
    }
}
