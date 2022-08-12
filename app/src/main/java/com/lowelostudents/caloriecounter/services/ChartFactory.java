package com.lowelostudents.caloriecounter.services;

import com.github.mikephil.charting.data.PieEntry;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ChartFactory {
    private static ChartFactory instance;

    public static synchronized ChartFactory getInstance() {
        if (instance == null) instance = new ChartFactory();

        return instance;
    }

    // TODO math helpers class
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // TODO maybe reflection dynamic or switchcase
    public List<PieEntry> generatePieEntries(Food nutrient, User user) {
        List<PieEntry> pieEntries = new ArrayList<>();

        if (nutrient.getCarbsCal() != 0)
            pieEntries.add(new PieEntry((float) nutrient.getCarbsCal(), "Carbs"));
        if (nutrient.getProteinCal() != 0)
            pieEntries.add(new PieEntry((float) nutrient.getProteinCal(), "Protein"));
        if (nutrient.getFatCal() != 0)
            pieEntries.add(new PieEntry((float) nutrient.getFatCal(), "Fat"));
        if (nutrient.getCarbsCal() == 0 || nutrient.getProteinCal() == 0 || nutrient.getFatCal() == 0)
            pieEntries.add(new PieEntry((float) (nutrient.getCalTotal() - (nutrient.getCarbsCal() + nutrient.getProteinCal() + nutrient.getFatCal()))));
        pieEntries.add(new PieEntry((float) (Math.max(0, user.getCalTotal() - nutrient.getCalTotal())), "Calories left"));

        return pieEntries;
    }
}
