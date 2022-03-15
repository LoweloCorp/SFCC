package com.lowelostudents.caloriecounter;

import static junit.framework.TestCase.assertEquals;

import android.util.Log;

import org.junit.Test;

import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.NutrientService;

import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UnitTests {
    @Test
    public void foodConstructor() {
        Food food = new Food("Name", 1, 1, 1, 3, 3);
        Food food2 = new Food("Name", 1, 1, 1, 3, 3);
        Food food3 = new Food("Name", 10, 5, 2);
        Food food4 = new Food("Name", 10, 5, 2);

        assertEquals(food3, food4);
        assertEquals(food, food2);
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


        assertEquals(3, food.getPortionSize());
        assertEquals(17, food.getCalPerPortion());
        assertEquals(17, food.getCalTotal());

        assertEquals(25, food2.getCalTotal());
    }

    @Test
    public void stringSimiliarity(){
        NormalizedLevenshtein levenshtein = new NormalizedLevenshtein();
        Levenshtein levenshteinigung = new Levenshtein();
        String stringOne = "Kohlbraten";
        String stringTwo = "tqwtqtqwtq";

        double distance = levenshtein.distance(stringOne, stringTwo);
        double distancet = levenshteinigung.distance(stringOne, stringTwo);

        System.out.println(String.valueOf(distance));
        System.out.println(String.valueOf(distancet));
    }
}