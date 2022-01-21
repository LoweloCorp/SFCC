package com.lowelostudents.caloriecounter.ui.foodhub;

import android.util.Log;

public class FoodhubController {
    private static FoodhubController foodhubControllerInstance;

    public static synchronized FoodhubController getInstance() {
        if (foodhubControllerInstance == null)
            foodhubControllerInstance = new FoodhubController();

        return foodhubControllerInstance;
    }

    public void createFood() {
        Log.i("foodCrated", "true");
    }

    public void createMeal() {
        Log.i("mealCreated", "true");
    }
}
