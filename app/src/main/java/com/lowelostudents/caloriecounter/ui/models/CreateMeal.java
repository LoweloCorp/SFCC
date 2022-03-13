package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.databinding.ActivityCreatemealBinding;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
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
    @Getter
    private ActivityMode mode = ActivityMode.CREATE;
    @Getter
    private Meal meal;

    @SneakyThrows
    protected void setEventHandlers(GenericRecyclerViewAdapter recyclerViewAdapter, ActivityMode mode) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method save = this.getClass().getMethod("save");
        Method finish = Activity.class.getMethod("finish");
        Method method = recyclerViewAdapter.getClass().getMethod("handleDatasetChanged", List.class);
        Method update = this.getClass().getMethod("update", Meal.class);

        eventHandlingService.onChangedInvokeMethod(this , this.dataSet, recyclerViewAdapter, method);
        if (mode == ActivityMode.CREATE) {
            eventHandlingService.onClickInvokeMethod(binding.confirmButton, this, save);
        } else {
            eventHandlingService.onClickInvokeMethod(binding.confirmButton, this, update, this.meal);
        }

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
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            this.mode = (ActivityMode) bundle.get("mode");
            this.meal = (Meal) bundle.get("item");
            Log.i("Mode", this.mode.toString());
            Log.i("Meal", this.meal.toString());
        }

        if(this.mode == ActivityMode.UPDATE) binding.deleteForeverButton.setVisibility(View.VISIBLE);
        Log.i("mode", this.mode.toString());

        setEventHandlers(recyclerViewAdapter, this.mode);
    }

    public void save() {
        MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

        mealViewModel.insert(binding.mealName.getText().toString());
    }

    public void update(Meal meal) {
        MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

        mealViewModel.update(meal);
    }
}