package com.lowelostudents.caloriecounter.ui.actions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import com.lowelostudents.caloriecounter.ui.CRUDViewModel;
import com.lowelostudents.caloriecounter.databinding.ActivityCreateMealBinding;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.foodhub.MealViewModel;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

public class CreateMeal extends AppCompatActivity {

    private ActivityCreateMealBinding binding;
    private MealViewModel model;

    @SneakyThrows
    private void setEventHandlers(CRUDViewModel<?> crudViewModel) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();

        Method finish = crudViewModel.getClass().getMethod("finish", Activity.class);
        Method insert = crudViewModel.getClass().getMethod("insert", Object.class);

        Meal meal = new Meal("NameOFMeal");
        eventHandlingService.onClickInvokeMethod(binding.cancelButton, crudViewModel, finish, this);
        eventHandlingService.onClickInvokeMethod(binding.confirmButton, crudViewModel, insert, meal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MealViewModel.class);
        binding = ActivityCreateMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setEventHandlers(model);
    }

}