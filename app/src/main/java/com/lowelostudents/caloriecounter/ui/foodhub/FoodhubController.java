package com.lowelostudents.caloriecounter.ui.foodhub;

import android.app.Activity;
import android.util.Log;

import com.lowelostudents.caloriecounter.GenericController;

public class FoodhubController extends GenericController {
    private static FoodhubController foodhubControllerInstance;

    public static synchronized FoodhubController getInstance() {
        if (foodhubControllerInstance == null)
            foodhubControllerInstance = new FoodhubController();

        return foodhubControllerInstance;
    }

    @Override
    public <T extends Activity> void finish(T activity) {
        activity.finish();
    }

    public long create(Object... parameters) {
        Log.i("Test", "Test");
        return 0;
    }

    public long create(String x) {
        Log.i("Test", "Test" + x);
        return 0;
    }

    public void read() {

    }

    public long update(Object... paremeters) {
        return 0;
    }

    public void delete() {

    }
}
