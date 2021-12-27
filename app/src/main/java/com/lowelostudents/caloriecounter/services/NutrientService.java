package com.lowelostudents.caloriecounter.services;

import com.lowelostudents.caloriecounter.models.Food;

public class NutrientService {
    private static NutrientService nutrientService;

    public void calcCalories(Food food){
        food.setCarbsCal(food.getCarbsGram()* 4);
        food.setProteinCal(food.getProteinGram()*4);
        food.setFatCal(food.getFatGram()*9);
        if(food.getPortionSize() == 0)
            food.setPortionSize(food.getCarbsGram()+ food.getProteinGram()+ food.getFatGram());
        if(food.getCalPerPortion() == 0)
            food.setCalPerPortion(food.getFatCal()+food.getProteinCal()+ food.getCarbsCal());
        food.setCalPerGram((double) food.getCalPerPortion()/food.getPortionSize());
        food.setCalTotal((int) Math.round(food.getCalPerGram() * food.getGramTotal()));
    }

    public static synchronized NutrientService getNutrientService(){
        if(nutrientService == null) nutrientService = new NutrientService();

        return nutrientService;
    }
}
