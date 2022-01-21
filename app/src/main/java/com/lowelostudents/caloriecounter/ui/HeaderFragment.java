package com.lowelostudents.caloriecounter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.FragmentHeaderBinding;
import com.lowelostudents.caloriecounter.ui.foodhub.FoodhubController;
import com.lowelostudents.caloriecounter.ui.modals.CreateFood;
import com.lowelostudents.caloriecounter.ui.modals.CreateMeal;

public class HeaderFragment extends Fragment {

    private HeaderViewModel headerViewModel;
    private FragmentHeaderBinding binding;

    private void setEventListeners() {
        // TODO Search
        FoodhubController foodhubController = FoodhubController.getInstance();

        binding.createFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateFood.class);
                startActivity(intent);

                foodhubController.createFood();
            }
        });

        binding.createMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateMeal.class);
                startActivity(intent);

                foodhubController.createMeal();
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        headerViewModel = new ViewModelProvider(this).get(HeaderViewModel.class);

        binding = FragmentHeaderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setEventListeners();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}