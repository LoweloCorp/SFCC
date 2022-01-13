package com.lowelostudents.caloriecounter.ui.foodhub;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.databinding.FragmentFoodhubBinding;
import com.lowelostudents.caloriecounter.models.Day;
import com.lowelostudents.caloriecounter.models.Food;
import com.lowelostudents.caloriecounter.models.Meal;
import com.lowelostudents.caloriecounter.models.daos.DayDao;
import com.lowelostudents.caloriecounter.models.daos.Day_FoodDao;
import com.lowelostudents.caloriecounter.models.daos.FoodDao;
import com.lowelostudents.caloriecounter.ui.RecyclerViewAdapter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodhubFragment extends Fragment {

    private FoodhubViewModel foodhubViewModel;
    private FragmentFoodhubBinding binding;
    private ArrayList<Object> items = new ArrayList<>();
    private AppDatabase appdb;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.appdb = AppDatabase.getInMemoryInstance(context.getApplicationContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodhubViewModel =
                new ViewModelProvider(this).get(FoodhubViewModel.class);

        binding = FragmentFoodhubBinding.inflate(inflater, container, false);
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