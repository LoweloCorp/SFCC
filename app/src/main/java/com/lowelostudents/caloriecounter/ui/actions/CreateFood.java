package com.lowelostudents.caloriecounter.ui.actions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;


import com.lowelostudents.caloriecounter.ui.CRUDViewModel;
import com.lowelostudents.caloriecounter.databinding.ActivityCreateFoodBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.foodhub.FoodViewModel;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

//TODO generify

public class CreateFood extends AppCompatActivity {

    private ActivityCreateFoodBinding binding;
    private FoodViewModel model;

    @SneakyThrows
    private void setEventHandlers(CRUDViewModel<?> crudViewModel) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();

        Method finish = crudViewModel.getClass().getMethod("finish", Activity.class);
        Method insert = crudViewModel.getClass().getMethod("insert", Object.class);

        Food food = new Food("Name", 1, 1, 1, 3);
        eventHandlingService.onClickInvokeMethod(binding.cancelButton, crudViewModel, finish, this);
        eventHandlingService.onClickInvokeMethod(binding.confirmButton, crudViewModel, insert, food);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(FoodViewModel.class);
        binding = ActivityCreateFoodBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setEventHandlers(model);
    }

}