package com.lowelostudents.caloriecounter;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.models.Day;
import com.lowelostudents.caloriecounter.models.Meal;
import com.lowelostudents.caloriecounter.models.daos.DayDao;
import com.lowelostudents.caloriecounter.models.daos.Day_FoodDao;
import com.lowelostudents.caloriecounter.models.daos.MealDao;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;
import com.lowelostudents.caloriecounter.models.Food;
import com.lowelostudents.caloriecounter.models.daos.FoodDao;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class IntegrationTests {
    AppDatabase appdb = AppDatabase.getInMemoryInstance(InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext());

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.lowelostudents.caloriecounter", appContext.getPackageName());
    }

    @Test
    public void createAndReadDay(){
        DayDao dayDao = appdb.dayDao();
        Day_FoodDao day_foodDao = appdb.day_foodDao();
        Day day = new Day();

        dayDao.insertAll(day);

        List<Day_Food_Relation> dfr = day_foodDao.getAll();
        Calendar cal = Calendar.getInstance();

        assertEquals(cal.get(Calendar.DATE), dfr.get(0).getDay().getDayId());
        assertEquals(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()), dfr.get(0).getDay().getName());
        assertEquals(day.getDayId() , day_foodDao.getOne(cal.get(Calendar.DATE)).getDay().getDayId());
    }

    @Test
    public void createAndReadFood(){
        FoodDao foodDao = appdb.foodDao();
        Food food = new Food();
        Food food2 = new Food();

        foodDao.insertAll(food, food2);

        Food retrievedFood = foodDao.getOne(1);
        List<Food> retrievedFoods = foodDao.getAll();

        assertNotNull(retrievedFood);
        assertNotNull(retrievedFoods);
    }

    @Test
    public void createAndReadMeal(){
        MealDao mealDao = appdb.mealDao();
        Meal meal = new Meal("MyMeal");

        mealDao.insertall(meal);

        Meal retrievedMeal = mealDao.getOne(1);
        List<Meal> retrievedMeals = mealDao.getAll();

        assertNotNull(retrievedMeal);
        assertNotNull(retrievedMeals);
    }

    //TODO RelationTests
}