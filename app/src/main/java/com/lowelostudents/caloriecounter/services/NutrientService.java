package com.lowelostudents.caloriecounter.services;

import com.lowelostudents.caloriecounter.models.Food;

public class NutrientService {

    public void calcCalories(Food food){
        food.setCarbsCal(food.getCarbsGram()* 4);
        food.setProteinCal(food.getProteinGram()*4);
        food.setFatCal(food.getFatGram()*9);
        if(food.getPortionSize() == 0)
            food.setPortionSize(food.getCarbsGram()+ food.getProteinGram()+ food.getFatGram());
        if(food.getCalPerPortion() == 0)
            food.setCalPerPortion(food.getFatCal()+food.getProteinCal()+ food.getCarbsCal());
        food.setCalPerGram((int) Math.round( (double) food.getCalPerPortion()/food.getPortionSize()));
        food.setCalTotal(food.getCalPerGram() * food.getGramTotal());
    }
}
