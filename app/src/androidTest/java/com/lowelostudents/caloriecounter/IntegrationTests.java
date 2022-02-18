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
import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.entities.Day_Food;
import com.lowelostudents.caloriecounter.services.GenericQueryService;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Meal_Food;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;

import java.io.File;
import java.util.ArrayList;
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
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    AppDatabase appdb = AppDatabase.getInMemoryInstance(context);

    Day.DayDao dayDao;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.lowelostudents.caloriecounter", appContext.getPackageName());
    }

    @Test
    public void createAndReadDay(){
        Day.DayDao dayDao = appdb.dayDao();
        Day_Food.Day_FoodDao day_foodDao = appdb.day_foodDao();
        Day day = new Day();

        dayDao.insert(day);

        List<Day_Food_Relation> dfr = day_foodDao.getAll();
        Calendar cal = Calendar.getInstance();

        assertEquals(cal.get(Calendar.DATE), dfr.get(0).getDay().getDayId());
        assertEquals(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()), dfr.get(0).getDay().getName());
        assertEquals(day.getDayId() , day_foodDao.getByDate(cal.get(Calendar.DATE)).getDay().getDayId());
    }

    @Test
    public void createAndReadFood(){
        Food.FoodDao foodDao = appdb.foodDao();
        List<Food> foods = new ArrayList<>();
        foods.add(new Food());
        foods.add(new Food());

        foodDao.insert(foods);

        Food retrievedFood = foodDao.get(Food.class, 1);
        List<Food> genericFoodList = foodDao.getAll(Food.class);
        Food genericFood = foodDao.get(Food.class, 2);

        List<Food> retrievedFoods = foodDao.getAll(Food.class);

        assertNotNull(genericFoodList);
        assertNotNull(genericFood);
        assertNotNull(retrievedFood);
        assertNotNull(retrievedFoods);
    }

    @Test
    public void createAndReadMeal(){
        Meal.MealDao mealDao = appdb.mealDao();
        Meal meal = new Meal("MyMeal");

        mealDao.insert(meal);

        Meal retrievedMeal = mealDao.get(Meal.class, 1);
        List<Meal> retrievedMeals = mealDao.getAll(Meal.class);

        assertNotNull(retrievedMeal);
        assertNotNull(retrievedMeals);
    }

    //TODO RelationTests

    @Test
    public void createAndReadDayFood(){
        Day.DayDao dayDao = appdb.dayDao();
        Food.FoodDao foodDao = appdb.foodDao();
        Day_Food.Day_FoodDao day_foodDao = appdb.day_foodDao();

        Food food = new Food();
        Day day = new Day();

        // insertAll Returns array of generated IDs for inserted entities, so that I do not need to query them to create the relation
        long foodId = foodDao.insert(food);
        long dayId;

        try {
            dayId = dayDao.insert(day);
        } catch (SQLiteConstraintException e) {
            Calendar cal = Calendar.getInstance();

            dayId = dayDao.get(Day.class, cal.get(Calendar.DATE)).getDayId();

            Log.w("Day already exists", Arrays.toString(e.getStackTrace()));
        }

        Day_Food dayFood = new Day_Food();
        dayFood.setFoodId(foodId);
        dayFood.setDayId((int) dayId);

        day_foodDao.insert(dayFood);

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
        Meal.MealDao mealDao = appdb.mealDao();
        Food.FoodDao foodDao = appdb.foodDao();
        Meal_Food.Meal_FoodDao meal_foodDao = appdb.meal_foodDao();

        Food food = new Food();
        Meal meal= new Meal("HappyMeal");

        // insertAll Returns array of generated IDs for inserted entities, so that I do not need to query them to create the relation
        long foodId = foodDao.insert(food);
        long mealId = mealDao.insert(meal);

        Meal_Food mealFood = new Meal_Food();
        mealFood.setFoodId(foodId);
        mealFood.setMealId(mealId);

        meal_foodDao.insert(mealFood);
        Meal_Food_Relation mealFoods = meal_foodDao.getAll().get(0);
        List<Food> retrievedFoods = mealFoods.getFoods();
        Meal retrievedMeal = mealFoods.getMeal();

        assertNotNull(mealFoods);
        assertNotNull(retrievedFoods);
        assertNotNull(retrievedMeal);

        assertEquals(foodId, retrievedFoods.get(0).getFoodId());
        assertEquals(mealId, retrievedMeal.getMealId());
    }

    public void testDBSize(){
        int foodAmount = 20;
        int dayAmount = 2000;
        Day[] day = new Day[dayAmount];
        Food[] food = new Food[foodAmount];
        Day_Food[] day_foods = new Day_Food[dayAmount];

        Day.DayDao dayDao = appdb.dayDao();
        Food.FoodDao foodDao = appdb.foodDao();
        Day_Food.Day_FoodDao day_foodDao = appdb.day_foodDao();

        for (int i = 0; i < dayAmount; i++) {
            day[i] = new Day();
            day[i].setDayId(i);
        }

        for (int i = 0; i < foodAmount ; i++) {
            food[i] = new Food("Name", 1, 1, 1, 3);
        }

        Long[] dayIds;
        dayIds = dayDao.insert(Arrays.asList(day));
        Long[] foodIds = foodDao.insert(Arrays.asList(food));

        for (int i = 0; i < dayAmount; i++) {
            for (int z = 0; z < foodAmount ; z++) {
                day_foods[z] = new Day_Food();
                day_foods[z].setDayId((int) dayIds[i].intValue());
                day_foods[z].setFoodId((int) foodIds[z].intValue());
                day_foodDao.insert(day_foods[z]);
            }
        }
        File file = context.getDatabasePath(appdb.getOpenHelper().getDatabaseName());

        Log.i("dBSIZE", String.valueOf(file.length()));
    }
}