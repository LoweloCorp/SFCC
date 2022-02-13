package com.lowelostudents.caloriecounter.ui.actions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.FragmentFoodBinding;
import com.lowelostudents.caloriecounter.databinding.FragmentMealBinding;
import com.lowelostudents.caloriecounter.ui.foodhub.FoodViewModel;
import com.lowelostudents.caloriecounter.ui.foodhub.MealViewModel;

public class MealFragment extends Fragment {
    private MealViewModel model;
    private FragmentMealBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model =
                new ViewModelProvider(this).get(MealViewModel.class);

        binding = FragmentMealBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
