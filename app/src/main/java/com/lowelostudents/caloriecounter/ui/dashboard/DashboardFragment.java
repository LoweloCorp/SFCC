package com.lowelostudents.caloriecounter.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.databinding.FragmentDashboardBinding;
import com.lowelostudents.caloriecounter.models.Food;
import com.lowelostudents.caloriecounter.models.Meal;
import com.lowelostudents.caloriecounter.ui.RecyclerViewAdapter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private ArrayList<Object> items = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        items.add(new Food("Name", 1, 1, 1, 3));
        items.add(new Food("AndererName", 1, 1, 1, 3));
        items.add(new Food("AndererName", 1, 1, 1, 3));
        items.add(new Food("AndererName", 1, 1, 1, 3));
        items.add(new Food("AndererName", 1, 1, 1, 3));
        items.add(new Food("AndererName", 1, 1, 1, 3));

        items.add(new Meal("Mahlzeit"));
        items.add(new Meal("Mahlzeit2"));
        items.add(new Meal("Mahlzeit2"));
        items.add(new Meal("Mahlzeit2"));
        items.add(new Meal("Mahlzeit2"));
        items.add(new Meal("Mahlzeit2"));
        items.add(new Meal("Mahlzeit2"));

        final RecyclerView foodList = binding.foodList;
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this.getContext(), items);
        foodList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodList.setAdapter(recyclerViewAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}