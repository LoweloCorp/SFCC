package com.lowelostudents.caloriecounter;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.models.Day;
import com.lowelostudents.caloriecounter.models.Day_Food;
import com.lowelostudents.caloriecounter.models.Meal;
import com.lowelostudents.caloriecounter.models.Meal_Food;
import com.lowelostudents.caloriecounter.models.daos.DayDao;
import com.lowelostudents.caloriecounter.models.daos.Day_FoodDao;
import com.lowelostudents.caloriecounter.models.daos.MealDao;
import com.lowelostudents.caloriecounter.models.daos.Meal_FoodDao;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;
import com.lowelostudents.caloriecounter.models.Food;
import com.lowelostudents.caloriecounter.models.daos.FoodDao;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.util.Arrays;
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

    @Test
    public void createAndReadDayFood(){
        DayDao dayDao = appdb.dayDao();
        FoodDao foodDao = appdb.foodDao();
        Day_FoodDao day_foodDao = appdb.day_foodDao();

        Food food = new Food();
        Day day = new Day();

        // insertAll Returns array of generated IDs for inserted entities, so that I do not need to query them to create the relation
        long foodId = foodDao.insertAll(food)[0];
        long dayId;

        try {
            dayId = dayDao.insertAll(day)[0];
        } catch (SQLiteConstraintException e) {
            Calendar cal = Calendar.getInstance();
            dayId = dayDao.getOne(cal.get(Calendar.DATE)).getDayId();
            Log.w("Day already exists", Arrays.toString(e.getStackTrace()));
        }

        Day_Food dayFood = new Day_Food();
        dayFood.setFoodId(foodId);
        dayFood.setDayId((int) dayId);

        day_foodDao.insertAll(dayFood);

        Day_Food_Relation dayFoods = day_foodDao.getAll().get(0);
        List<Food> retrievedFoods = dayFoods.getFoods();
        Day retrievedDay = dayFoods.getDay();

        assertNotNull(dayFoods);
        assertNotNull(retrievedFoods);
        assertNotNull(retrievedDay);

        assertEquals(foodId, retrievedFoods.get(0).getFoodId());
        assertEquals(dayId, retrievedDay.getDayId());
    }

    @Test
    public void createAndReadMealFood(){
        MealDao mealDao = appdb.mealDao();
        FoodDao foodDao = appdb.foodDao();
        Meal_FoodDao meal_foodDao = appdb.meal_foodDao();

        Food food = new Food();
        Meal meal= new Meal("HappyMeal");

        // insertAll Returns array of generated IDs for inserted entities, so that I do not need to query them to create the relation
        long foodId = foodDao.insertAll(food)[0];
        long mealId = mealDao.insertall(meal)[0];

        Meal_Food mealFood = new Meal_Food();
        mealFood.setFoodId(foodId);
        mealFood.setMealId(mealId);

        meal_foodDao.insertAll(mealFood);

        Meal_Food_Relation mealFoods = meal_foodDao.getAll().get(0);
        List<Food> retrievedFoods = mealFoods.getFoods();
        Meal retrievedMeal = mealFoods.getMeal();

        assertNotNull(mealFoods);
        assertNotNull(retrievedFoods);
        assertNotNull(retrievedMeal);

        assertEquals(foodId, retrievedFoods.get(0).getFoodId());
        assertEquals(mealId, retrievedMeal.getMealId());
    }
}