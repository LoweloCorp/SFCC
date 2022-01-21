package com.lowelostudents.caloriecounter.ui.modals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;


import com.lowelostudents.caloriecounter.databinding.ActivityCreateFoodBinding;

public class CreateFood extends AppCompatActivity {

    private ActivityCreateFoodBinding binding;
    private CreateFoodViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(CreateFoodViewModel.class);
        binding = ActivityCreateFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

}