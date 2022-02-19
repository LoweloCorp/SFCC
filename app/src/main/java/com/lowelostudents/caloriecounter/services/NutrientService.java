package com.lowelostudents.caloriecounter.services;

import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;

import java.util.List;

//TODO calculation water
public class NutrientService {
    private static NutrientService nutrientService;

    public static synchronized NutrientService getInstance() {
        if (nutrientService == null) nutrientService = new NutrientService();

        return nutrientService;
    }

    public void calcCalories(Food food) {
        food.setCarbsCal(food.getCarbsGram() * 4);
        food.setProteinCal(food.getProteinGram() * 4);
        food.setFatCal(food.getFatGram() * 9);
        if (food.getPortionSize() == 0)
            // TODO CHECK wrong calculation if water
            food.setPortionSize(food.getCarbsGram() + food.getProteinGram() + food.getFatGram());
        if (food.getCalPerPortion() == 0)
            food.setCalPerPortion(food.getFatCal() + food.getProteinCal() + food.getCarbsCal());
        food.setCalPerGram((double) food.getCalPerPortion() / food.getPortionSize());
        food.setCalTotal((int) Math.round(food.getCalPerGram() * food.getGramTotal()));
    }

    public void calcCalories(Meal meal, List<Food> foodList) {
        foodList.forEach(food -> {
            meal.setCalTotal(meal.getCalTotal() + food.getCalTotal());
            meal.setGramTotal(meal.getGramTotal() + food.getGramTotal());
        });
    }
}
