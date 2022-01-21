package com.lowelostudents.caloriecounter.ui.modals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;

import com.lowelostudents.caloriecounter.databinding.ActivityCreateMealBinding;

public class CreateMeal extends AppCompatActivity {

    private ActivityCreateMealBinding binding;
    private CreateMealViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(CreateMealViewModel.class);
        binding = ActivityCreateMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

}