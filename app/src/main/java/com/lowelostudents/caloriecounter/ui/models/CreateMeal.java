package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.data.LiveDataTuple;
import com.lowelostudents.caloriecounter.databinding.ActivityCreatemealBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.CreateMealRecyclerViewAdapter;
import com.lowelostudents.caloriecounter.ui.GenericRecyclerViewAdapter;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.lang.reflect.Method;
import java.util.List;

import lombok.Getter;
import lombok.SneakyThrows;

public class CreateMeal extends AppCompatActivity {
    @Getter
    private ActivityCreatemealBinding binding;
    @Getter
    private MealViewModel model;
    private LiveData<List<Food>> dataSet;
    private CreateMealRecyclerViewAdapter recyclerViewAdapter;

    @SneakyThrows
    protected void setEventHandlers(GenericRecyclerViewAdapter recyclerViewAdapter) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method insert = this.getClass().getMethod("insert");
        Method finish = Activity.class.getMethod("finish");
        Method method = recyclerViewAdapter.getClass().getMethod("handleDatasetChanged", List.class);

        eventHandlingService.onChangedInvokeMethod(this , this.dataSet, recyclerViewAdapter, method);
        eventHandlingService.onClickInvokeMethod(binding.confirmButton, this, insert);
        eventHandlingService.onClickInvokeMethod(binding.cancelButton, this, finish);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCreatemealBinding.inflate(getLayoutInflater());
        this.model = new ViewModelProvider(this).get(MealViewModel.class);
        FoodViewModel foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        setContentView(binding.getRoot());

        this.dataSet = foodViewModel.getFoods();

        final RecyclerView foodList = binding.foodList;
        this.recyclerViewAdapter = new CreateMealRecyclerViewAdapter(this);
        foodList.setLayoutManager(new LinearLayoutManager(this));
        foodList.setAdapter(recyclerViewAdapter);

        setEventHandlers(recyclerViewAdapter);
    }

    public void insert() {
        MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

        mealViewModel.insert(binding.mealName.getText().toString());
    }
}