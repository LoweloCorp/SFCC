package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

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
import com.lowelostudents.caloriecounter.services.FilterService;
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
        Context context = getApplicationContext();
        int toastDuration = Toast.LENGTH_SHORT;
        Method finish = Activity.class.getMethod("finish");
        Method handleDatasetChanged = recyclerViewAdapter.getClass().getMethod("handleDatasetChanged", List.class);

        // TODO use filter service for search
        if (mode == ActivityMode.CREATE) {
            binding.confirmButton.setOnClickListener(view -> {
                boolean validated = validate();
                if (validated) {
                    save();
                    CharSequence info = "Meal saved";
                    Toast toast = Toast.makeText(context, info, toastDuration);
                    toast.show();
                    finish();
                }
            });
        } else {
            binding.deleteForeverButton.setOnClickListener( view -> {
                delete(this.meal);
                CharSequence info = "Meal deleted";
                Toast toast = Toast.makeText(context, info, toastDuration);
                toast.show();
                finish();
            });
            binding.confirmButton.setOnClickListener(view -> {
                update(this.meal);
                CharSequence info = "Meal updated";
                Toast toast = Toast.makeText(context, info, toastDuration);
                toast.show();
                finish();
            });
        }

        eventHandlingService.onChangedInvokeMethod(this, this.dataSet, recyclerViewAdapter, handleDatasetChanged);
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

        this.binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        if (bundle != null) {
            this.mode = (ActivityMode) bundle.get("mode");
            this.meal = (Meal) bundle.get("item");
            Log.i("Mode", this.mode.toString());
            Log.i("Meal", this.meal.toString());
        }

        if (this.mode == ActivityMode.UPDATE)
            binding.deleteForeverButton.setVisibility(View.VISIBLE);
        Log.i("mode", this.mode.toString());

        setEventHandlers(recyclerViewAdapter, this.mode);
    }

    public void save() {
        if (validate()) {
            MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

            mealViewModel.insert(binding.mealName.getText().toString());
        }
    }

    public void update(Meal meal) {
        if (validate()) {
            MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

            mealViewModel.update(meal, binding.mealName.getText().toString());
        }
    }


    public void delete(Meal meal) {
        MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

        mealViewModel.delete(meal);
    }

    private boolean validate() {
        boolean validated = true;

        if (!this.binding.mealName.getText().toString().matches("[a-zA-Z]+")) {
            this.binding.mealName.setError("Please enter with characters A-Z a-z");
            validated = false;
        }

        return validated;
    }
}