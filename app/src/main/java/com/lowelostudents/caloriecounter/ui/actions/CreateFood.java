package com.lowelostudents.caloriecounter.ui.actions;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;


//import com.lowelostudents.caloriecounter.databinding.ActivityCreateFoodBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.foodhub.FoodViewModel;
import com.lowelostudents.caloriecounter.ui.foodhub.MealViewModel;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

//TODO generify

public class CreateFood extends CRUDFragment<Food> {

    EventHandlingService eventHandlingService = EventHandlingService.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(new ViewModelProvider(this).get(FoodViewModel.class));
        Fragment mealFragment = new MealFragment();

        getSupportFragmentManager().beginTransaction().add(this.getBinding().getRoot().getId(), mealFragment).commit();

        Food food = new Food("FatFood", 1, 1,1, 3);
        save(food);
    }

    @Override @SneakyThrows
    protected void save(Food food) {
        Method insert = this.getModel().getClass().getMethod("insert", Object.class);
        eventHandlingService.onClickInvokeMethod(this.getBinding().confirmButton, this.getModel(), insert, food);
    }
}