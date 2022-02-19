package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.ActivityCreatefoodBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.SneakyThrows;

//TODO generify

public class CreateFood extends AppCompatActivity {
    @Getter
    private ActivityCreatefoodBinding binding;
    @Getter
    private FoodViewModel model;


    @SneakyThrows
    protected void setEventHandlers(Food meal) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method insert = this.getModel().getClass().getMethod("insert", Food.class);
        Method finish = Activity.class.getMethod("finish");

        eventHandlingService.onClickInvokeMethod(binding.confirmButton, this.getModel(), insert, meal);
        eventHandlingService.onClickInvokeMethod(binding.cancelButton, this, finish);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCreatefoodBinding.inflate(getLayoutInflater());
        this.model = new ViewModelProvider(this).get(FoodViewModel.class);
        setContentView(binding.getRoot());

        Food food = new Food("MeinFressen", 1, 1, 1, 1);
        setEventHandlers(food);
    }
}