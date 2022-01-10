package com.lowelostudents.caloriecounter.ui.foodhub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.databinding.FragmentFoodhubBinding;
import com.lowelostudents.caloriecounter.models.Food;
import com.lowelostudents.caloriecounter.models.Meal;

import java.util.ArrayList;

public class FoodhubFragment extends Fragment {

    private FoodhubViewModel foodhubViewModel;
    private FragmentFoodhubBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodhubViewModel =
                new ViewModelProvider(this).get(FoodhubViewModel.class);

        binding = FragmentFoodhubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Food> foods = new ArrayList<>();
        ArrayList<Meal> meals = new ArrayList<>();

        //TODO DayFoodRelation MealFoodRelation inverted or DayMeal relation
//        ArrayList<Meal_Food_Relation> mealFoods = new ArrayList<>();

        foods.add(new Food("Name", 1, 1, 1, 3));
        foods.add(new Food("AndererName", 1, 1, 1, 3));
        foods.add(new Food("AndererName", 1, 1, 1, 3));
        foods.add(new Food("AndererName", 1, 1, 1, 3));
        foods.add(new Food("AndererName", 1, 1, 1, 3));
        foods.add(new Food("AndererName", 1, 1, 1, 3));

        meals.add(new Meal("Mahlzeit"));
        meals.add(new Meal("Mahlzeit2"));
        meals.add(new Meal("Mahlzeit2"));
        meals.add(new Meal("Mahlzeit2"));
        meals.add(new Meal("Mahlzeit2"));
        meals.add(new Meal("Mahlzeit2"));
        meals.add(new Meal("Mahlzeit2"));

        final RecyclerView foodList = binding.foodList;
        final FoodListAdapter foodListAdapter = new FoodListAdapter(this.getContext(), meals);
        foodList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodList.setAdapter(foodListAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}