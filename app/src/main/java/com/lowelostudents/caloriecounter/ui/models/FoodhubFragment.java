package com.lowelostudents.caloriecounter.ui.models;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.MainActivity;
import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.data.LiveDataTuple;
import com.lowelostudents.caloriecounter.databinding.FragmentFoodhubBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.services.FilterService;
import com.lowelostudents.caloriecounter.ui.GenericRecyclerViewAdapter;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import info.debatty.java.stringsimilarity.Levenshtein;
import lombok.SneakyThrows;

public class FoodhubFragment extends Fragment {
    private FragmentFoodhubBinding binding;
    private LiveDataTuple<Meal, Food> dataSet;
    private boolean mealChecked = true;
    private boolean foodChecked = true;
    private GenericRecyclerViewAdapter recyclerViewAdapter;
    // TODO generify, interface
    @SneakyThrows
    private void setEventHandlers(Object recyclerViewAdapter) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method method = recyclerViewAdapter.getClass().getMethod("handleDatasetChanged", List.class);

        eventHandlingService.onChangedInvokeMethod(getViewLifecycleOwner(), this.dataSet, recyclerViewAdapter, method);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MealViewModel mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        FoodViewModel foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        this.binding = FragmentFoodhubBinding.inflate(inflater, container, false);
        this.dataSet = new LiveDataTuple<>(mealViewModel.getMeals(), foodViewModel.getFoods());

        recyclerViewAdapter = new GenericRecyclerViewAdapter(this.getContext());
        final RecyclerView foodList = binding.foodList;
        foodList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodList.setAdapter(recyclerViewAdapter);

        setEventHandlers(recyclerViewAdapter);

        this.binding.foodToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                foodChecked = b;
                List<Nutrients> allData = recyclerViewAdapter.getAllDataSet();

                if (!foodChecked) {
                    if(mealChecked)  {
                        recyclerViewAdapter.setDataSet(allData.stream().filter( item -> item.getClass() != Food.class).collect(Collectors.toList()));
                    } else {
                        recyclerViewAdapter.setDataSet(allData.stream().filter( item -> item.getClass() != Food.class && item.getClass() != Meal.class).collect(Collectors.toList()));
                    }
                } else {
                    if (mealChecked) {
                        recyclerViewAdapter.setDataSet(allData);
                    } else {
                        recyclerViewAdapter.setDataSet(allData.stream().filter( item -> item.getClass() != Meal.class).collect(Collectors.toList()));
                    }
                }

                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        this.binding.mealToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mealChecked = b;
                List<Nutrients> allData = recyclerViewAdapter.getAllDataSet();

                if(!mealChecked){
                    if(foodChecked) {
                        recyclerViewAdapter.setDataSet(allData.stream().filter( item -> item.getClass() != Meal.class).collect(Collectors.toList()));
                    } else {
                        recyclerViewAdapter.setDataSet(allData.stream().filter( item -> item.getClass() != Meal.class && item.getClass() != Food.class).collect(Collectors.toList()));
                    }
                } else {
                    if (foodChecked) {
                        recyclerViewAdapter.setDataSet(allData);
                    } else {
                        recyclerViewAdapter.setDataSet(allData.stream().filter( item -> item.getClass() != Food.class).collect(Collectors.toList()));
                    }
                }

                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        SearchView searchView = mainActivity.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                FilterService filterService = FilterService.getInstance();
                recyclerViewAdapter.setDataSet(filterService.filterListByLevenshtein(recyclerViewAdapter.getDataSet(), s));
                recyclerViewAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}