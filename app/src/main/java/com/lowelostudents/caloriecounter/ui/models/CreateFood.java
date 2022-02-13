package com.lowelostudents.caloriecounter.ui.models;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;


//import com.lowelostudents.caloriecounter.databinding.ActivityCreateFoodBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.CRUDActivity;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

//TODO generify

public class CreateFood extends CRUDActivity<Food> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init(new ViewModelProvider(this).get(FoodViewModel.class));
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Fragment mealFragment = new MealFragment();

        getSupportFragmentManager().beginTransaction().add(this.getBinding().getRoot().getId(), mealFragment).commit();

        Food food = new Food("FatFood", 1, 1,1, 3);
        save(food, eventHandlingService);
    }

    @Override @SneakyThrows
    protected void save(Food food, EventHandlingService eventHandlingService) {
        Method insert = this.getModel().getClass().getMethod("insert", Object.class);
        eventHandlingService.onClickInvokeMethod(this.getBinding().confirmButton, this.getModel(), insert, food);
    }
}