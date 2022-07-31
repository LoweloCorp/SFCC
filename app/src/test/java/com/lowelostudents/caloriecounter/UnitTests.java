package com.lowelostudents.caloriecounter;

import static junit.framework.TestCase.assertEquals;


import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.NutrientService;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// TODO fix and complete tests

public class UnitTests {


    static class Log {
        public static int d(String tag, String msg) {
            System.out.println("DEBUG: " + tag + ": " + msg);
            return 0;
        }

        public static int i(String tag, String msg) {
            System.out.println("INFO: " + tag + ": " + msg);
            return 0;
        }

        public static int w(String tag, String msg) {
            System.out.println("WARN: " + tag + ": " + msg);
            return 0;
        }

        public static int e(String tag, String msg) {
            System.out.println("ERROR: " + tag + ": " + msg);
            return 0;
        }

        // add other methods if required...
    }

    @Test
    public void foodConstructor() {
        Food food = new Food("Name", 1, 1, 1, 3, 3);
        Food food2 = new Food("Name", 1, 1, 1, 3, 3);

        assertEquals(food.getName(), food2.getName());
    }

    @Test
    public void dayConstructor(){
        Day day = new Day();
        Day day2 = new Day();

        assertEquals(day, day2);
    }

    @Test
    public void nutrientServiceCalories(){
        Food food = new Food(), food2 = new Food();
        food.setName("Name");
        food.setCarbsGram(1);
        food.setProteinGram(1);
        food.setFatGram(1);
        food.setGramTotal(3);
        food.setPortionSize(3);
        food2.setName("Name2");
        food2.setGramTotal(10);
        food2.setCalPerPortion(5);
        food2.setPortionSize(2);

        NutrientService ms = new NutrientService();
        ms.calculateNutrients(food);
        ms.calculateNutrients(food2);


        assertEquals(3d, food.getPortionSize());
        assertEquals(17d, food.getCalPerPortion());
        assertEquals(17d, food.getCalTotal());

        assertEquals(25d, food2.getCalTotal());
    }

    @Test
    public void nutrientServiceCaloriesDecimal(){
        Food food = new Food(), food2 = new Food();
        food.setName("Name");
        food.setCarbsGram(1.5);
        food.setProteinGram(1.5);
        food.setFatGram(1.5);
        food.setGramTotal(100.5);
        food.setPortionSize(100);


        NutrientService ms = new NutrientService();
        ms.calculateNutrients(food);

        assertEquals(100d, food.getPortionSize());
        assertEquals(25.5, food.getCalPerPortion());
        assertEquals(0.255, food.getCalPerGram());
        assertEquals(25.6275, food.getCalTotal());
    }

    @Test
    public void stringSimiliarity(){
        NormalizedLevenshtein levenshtein = new NormalizedLevenshtein();
        Levenshtein levenshteinigung = new Levenshtein();
        String stringOne = "Kohlbraten";
        String stringTwo = "tqwtqtqwtq";

        double distance = levenshtein.distance(stringOne, stringTwo);
        double distancet = levenshteinigung.distance(stringOne, stringTwo);

    }

    @Test
    public void combineNutrientsWithMultiplier() {
        NutrientService nutrientService = NutrientService.getInstance();
        Food food = new Food();
        ArrayList<Food> foods = new ArrayList<>();
        foods.add(new Food("One", 1, 1, 1, 3, 3));
        foods.add(new Food("Two", 1, 1, 1, 3, 3));
        nutrientService.calculateNutrients(foods.get(0));
        nutrientService.calculateNutrients(foods.get(1));
        Log.i("FOOD1", foods.get(0).toString());

        // calTOtal 34, gramTotal 6, carbskcal 8, protein 8, fat 18,


        // 68, 12, 16, 16, 36
        Food foot = new Food("FUT", foods, 2, AggregationType.FOOD);

        assertEquals(foot.getCalTotal(), 68d);
        assertEquals(foot.getGramTotal(), 12d);
        assertEquals(foot.getCarbsCal(), 16d);
        assertEquals(foot.getProteinCal(), 16d);
        assertEquals(foot.getFatCal(), 36d);
    }

    @Test
    public void foodAggregation() {
        NutrientService nutrientService = NutrientService.getInstance();
        ArrayList<Food> foods = new ArrayList<>();
        foods.add(new Food("One", 1, 1, 1, 3, 3));
        foods.add(new Food("Two", 1, 1, 1, 3, 3));
        nutrientService.calculateNutrients(foods.get(0));
        nutrientService.calculateNutrients(foods.get(1));

        Food result = nutrientService.createFoodAggregation(foods.get(0), 6, AggregationType.FOOD);

        assertEquals(result.getCalTotal(), 68d);
    }
}