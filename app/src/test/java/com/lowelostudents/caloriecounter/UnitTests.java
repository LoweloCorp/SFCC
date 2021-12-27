package com.lowelostudents.caloriecounter;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import com.lowelostudents.caloriecounter.models.Day;
import com.lowelostudents.caloriecounter.models.Food;
import com.lowelostudents.caloriecounter.services.NutrientService;

import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    @Test
    public void foodConstructor1() {
        Food food = new Food("Name", 1, 1, 1, 3);
        Food food2 = new Food("Name", 1, 1, 1, 3);

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
        Food food = new Food("Name", 1, 1, 1, 3);

        NutrientService ms = new NutrientService();
        ms.calcCalories(food);

        assertEquals(3, food.getPortionSize());
        assertEquals(17, food.getCalPerPortion());
        assertEquals(6, food.getCalPerGram());
        assertEquals(18, food.getCalTotal());
    }

    @Test
    public void typeConverterServiceDate(){

    }
}