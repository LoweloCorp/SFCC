package com.lowelostudents.caloriecounter.ui.actions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

import com.lowelostudents.caloriecounter.databinding.ActivityCreateMealBinding;
import com.lowelostudents.caloriecounter.ui.foodhub.MealViewModel;

public class CreateMeal extends AppCompatActivity {

    private ActivityCreateMealBinding binding;
    private MealViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MealViewModel.class);
        binding = ActivityCreateMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

}