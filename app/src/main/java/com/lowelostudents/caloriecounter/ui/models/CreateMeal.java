package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.ActivityCreatemealBinding;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.SneakyThrows;

public class CreateMeal extends AppCompatActivity {
    @Getter
    private ActivityCreatemealBinding binding;
    @Getter
    private MealViewModel model;

    @SneakyThrows
    protected void setEventHandlers(Meal meal) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method insert = this.model.getClass().getMethod("insert", Meal.class);
        Method finish = Activity.class.getMethod("finish");

        eventHandlingService.onClickInvokeMethod(binding.confirmButton, this.getModel(), insert, meal);
        eventHandlingService.onClickInvokeMethod(binding.cancelButton, this, finish);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCreatemealBinding.inflate(getLayoutInflater());
        this.model = new ViewModelProvider(this).get(MealViewModel.class);
        setContentView(binding.getRoot());

        Meal meal = new Meal("Mealeee");
        setEventHandlers(meal);
    }
}