package com.lowelostudents.caloriecounter.services;

import com.github.mikephil.charting.data.PieEntry;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.models.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ChartFactory {
    private static ChartFactory instance;

    public static synchronized ChartFactory getInstance() {
        if (instance == null) instance = new ChartFactory();

        return instance;
    }

    public <T extends Nutrients> List<PieEntry> generatePieEntries(List<T> nutrientList) {
        List<PieEntry> pieEntries = new ArrayList<>();

        nutrientList.forEach(nutrient -> {
            pieEntries.add(new PieEntry(nutrient.getCarbsCal(), "Carbs"));
            pieEntries.add(new PieEntry(nutrient.getProteinCal(), "Protein"));
            pieEntries.add(new PieEntry(nutrient.getFatCal(), "Fat"));
//            pieEntries.add(new PieEntry(user.getCalTotal() - nutrient.getCalTotal(), "Calories left"));
        });

        return pieEntries;
    }

    public <T extends Nutrients> List<PieEntry> generatePieEntries(T nutrient) {
        List<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(nutrient.getCarbsCal(), "Carbs"));
        pieEntries.add(new PieEntry(nutrient.getProteinCal(), "Protein"));
        pieEntries.add(new PieEntry(nutrient.getFatCal(), "Fat"));
//        pieEntries.add(new PieEntry(user.getCalTotal() - nutrient.getCalTotal(), "Calories left"));

        return pieEntries;
    }
}
