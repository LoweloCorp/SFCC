package com.lowelostudents.caloriecounter.ui.foodhub;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.GenericViewModel;
import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.databinding.FragmentFoodhubBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.ui.RecyclerViewAdapter;

import java.util.ArrayList;

public class FoodhubFragment extends Fragment {


    //TODO Fix Livedata

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

        GenericViewModel<?> genericViewModel =
                new ViewModelProvider(this).get(GenericViewModel.class);

        MealViewModel mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        FoodViewModel foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.insert(new Food());
        foodViewModel.insert(new Food());

        items.add(new Food());

        Log.i("MYFOOD", String.valueOf(foodViewModel.getFoods().getValue()));
        Log.i("MYFOOD", String.valueOf(foodViewModel.getFoods()));

        binding = FragmentFoodhubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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