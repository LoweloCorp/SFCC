package com.lowelostudents.caloriecounter.ui.models;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

//import com.lowelostudents.caloriecounter.databinding.ActivityCreateMealBinding;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.CRUDActivity;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

public class CreateMeal extends CRUDActivity<Meal> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init(new ViewModelProvider(this).get(MealViewModel.class));
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Fragment mealFragment = new MealFragment();

        getSupportFragmentManager().beginTransaction().add(this.getBinding().getRoot().getId(), mealFragment).commit();

        Meal meal = new Meal("Mealeee");
        save(meal, eventHandlingService);
    }

    @Override @SneakyThrows
    protected void save(Meal meal, EventHandlingService eventHandlingService) {
        Method insert = this.getModel().getClass().getMethod("insert", Object.class);
        eventHandlingService.onClickInvokeMethod(this.getBinding().confirmButton, this.getModel(), insert, meal);
    }
}