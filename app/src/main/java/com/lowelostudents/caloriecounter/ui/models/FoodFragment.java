package com.lowelostudents.caloriecounter.ui.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.FragmentFoodBinding;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;

public class FoodFragment extends Fragment {

    private FoodViewModel model;
    private FragmentFoodBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.model =
                new ViewModelProvider(this).get(FoodViewModel.class);
        this.binding = FragmentFoodBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
