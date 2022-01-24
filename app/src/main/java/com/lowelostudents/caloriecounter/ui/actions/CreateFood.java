package com.lowelostudents.caloriecounter.ui.actions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;


import com.lowelostudents.caloriecounter.GenericViewModel;
import com.lowelostudents.caloriecounter.databinding.ActivityCreateFoodBinding;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.foodhub.FoodViewModel;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

public class CreateFood extends AppCompatActivity {

    private ActivityCreateFoodBinding binding;
    private FoodViewModel model;

    @SneakyThrows
    private void setEventHandlers() {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        GenericViewModel<?> foodhubController = model;

        Method finish = foodhubController.getClass().getMethod("finish", Activity.class);


        eventHandlingService.onClickInvokeMethod(binding.cancelButton, foodhubController, finish, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(FoodViewModel.class);
        binding = ActivityCreateFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setEventHandlers();
    }

}