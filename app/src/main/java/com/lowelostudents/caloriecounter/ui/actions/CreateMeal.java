package com.lowelostudents.caloriecounter.ui.actions;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import com.lowelostudents.caloriecounter.databinding.ActivityCreateMealBinding;
import com.lowelostudents.caloriecounter.databinding.FragmentMealBinding;
import com.lowelostudents.caloriecounter.databinding.FragmentPersistenceBinding;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.CRUDViewModel;
import com.lowelostudents.caloriecounter.ui.foodhub.MealViewModel;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

public class CreateMeal extends CRUDFragment<Meal> {
    EventHandlingService eventHandlingService = EventHandlingService.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(new ViewModelProvider(this).get(MealViewModel.class));
        Fragment mealFragment = new MealFragment();

        getSupportFragmentManager().beginTransaction().add(this.getBinding().getRoot().getId(), mealFragment).commit();

        Meal meal = new Meal("Meal");
        save(meal);
    }

    @Override @SneakyThrows
    protected void save(Meal meal) {
        Method insert = this.getModel().getClass().getMethod("insert", Object.class);
        eventHandlingService.onClickInvokeMethod(this.getBinding().confirmButton, this.getModel(), insert, meal);
    }
}