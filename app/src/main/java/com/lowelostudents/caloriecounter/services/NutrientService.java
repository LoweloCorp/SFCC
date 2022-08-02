package com.lowelostudents.caloriecounter.services;

import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.ArrayList;
import java.util.List;

public class NutrientService {
    private static NutrientService nutrientService;

    public static synchronized NutrientService getInstance() {
        if (nutrientService == null) nutrientService = new NutrientService();

        return nutrientService;
    }

    public void calculateNutrients(Food nutrients) {
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

    public void combineNutrients(Food nutrients, List<Food> nutrientList) {
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

    // TODO apply multiplier to all affected fields not only the UI affecting ones
    public void applyMultiplier(Food nutrients, double multiplier) {
        nutrients.setFatCal(nutrients.getFatCal() * multiplier);
        nutrients.setProteinCal(nutrients.getProteinCal() * multiplier);
        nutrients.setCarbsCal(nutrients.getCarbsCal() * multiplier);
        nutrients.setCalTotal(nutrients.getCalTotal() * multiplier);
        nutrients.setGramTotal(nutrients.getGramTotal() * multiplier);
    }

    // TODO See usage because of instantiation maybe relevant
    public Food combineNutrients(List<Food> nutrientList) {
        Food nutrients = new Food();

        this.combineNutrients(nutrients, nutrientList);

        return nutrients;
    }

    public Food createFoodAggregation(Food data, double quantity, AggregationType aggregationType) {
        double multiplier = quantity / data.getGramTotal();
        double necessaryFoodInstances = Math.ceil(multiplier);
        List<Food> foodList = new ArrayList<>();

        for (int i = 0; i < necessaryFoodInstances; i++) {
            foodList.add(data);
        }

        return new Food(data.getName(), foodList, multiplier, aggregationType);
    }
}
