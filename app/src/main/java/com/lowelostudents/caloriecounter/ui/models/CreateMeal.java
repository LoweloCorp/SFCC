package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lowelostudents.caloriecounter.MainActivity;
import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.databinding.ActivityCreatemealBinding;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.services.FilterService;
import com.lowelostudents.caloriecounter.ui.CreateMealRecyclerViewAdapter;
import com.lowelostudents.caloriecounter.ui.GenericRecyclerViewAdapter;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;
import lombok.Getter;
import lombok.SneakyThrows;

public class CreateMeal extends AppCompatActivity {
    @Getter
    private ActivityCreatemealBinding binding;
    @Getter
    private MealViewModel model;
    private final Subject<List<Food>> dataSet = BehaviorSubject.create();
    private CreateMealRecyclerViewAdapter recyclerViewAdapter;
    @Getter
    private ActivityMode mode = ActivityMode.CREATE;
    @Getter
    private Food meal;

    private final ArrayList<Disposable> disposables = new ArrayList<>();

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
            binding.deleteForeverButton.setOnClickListener(view -> {
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

        // TODO potentially deprecatable can call method on filter instead of observing
//        eventHandlingService.onChangedInvokeMethod(this, this.dataSet, recyclerViewAdapter, handleDatasetChanged);

        Disposable disposable = this.dataSet.observeOn(AndroidSchedulers.mainThread()).subscribe(recyclerViewAdapter::handleDatasetChanged);

        this.disposables.add(disposable);
        eventHandlingService.onClickInvokeMethod(binding.cancelButton, this, finish);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCreatemealBinding.inflate(getLayoutInflater());
        this.model = new ViewModelProvider(this).get(MealViewModel.class);
        FoodViewModel foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        setContentView(binding.getRoot());

        if (this.mode == ActivityMode.CREATE) {
            binding.mealTabLayout.selectTab(binding.mealTabLayout.getTabAt(1));
            foodViewModel.getFoods().take(1).subscribe(foods -> this.dataSet.onNext(foods));
        }

        final RecyclerView foodList = binding.foodList;
        this.recyclerViewAdapter = new CreateMealRecyclerViewAdapter(this);
        this.recyclerViewAdapter.setActivityMode(this.mode);
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
            this.meal = (Food) bundle.get("item");
        }

        if (this.mode == ActivityMode.UPDATE) {
            binding.mealTabLayout.selectTab(binding.mealTabLayout.getTabAt(0));
            this.model.getMealFoods(this.meal.getId()).take(1).subscribe( mealFoods -> this.dataSet.onNext(mealFoods.getFoods()));
        }

        // TODO
        binding.mealTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    if (mode == ActivityMode.CREATE) {
                        recyclerViewAdapter.handleDatasetChanged(new ArrayList<>(recyclerViewAdapter.getMealViewModel().checkedFoods.values()));
                    } else {
                        model.getMealFoods(meal.getId()).take(1).subscribe( mealWithFood -> {
                            ArrayList<Food> nutrients = new ArrayList<>(mealWithFood.getFoods());
                            nutrients.addAll(recyclerViewAdapter.getMealViewModel().checkedFoods.values());

                            dataSet.onNext(nutrients);
                        });
                    }
                } else {
                        foodViewModel.getFoods().take(1).subscribe(foods -> dataSet.onNext(foods));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (this.mode == ActivityMode.UPDATE)
            binding.deleteForeverButton.setVisibility(View.VISIBLE);

        setEventHandlers(recyclerViewAdapter, this.mode);
    }

    public void save() {
        if (validate()) {
            MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

            mealViewModel.insert(binding.mealName.getText().toString());
        }
    }

    public void update(Food meal) {
        if (validate()) {
            MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

            mealViewModel.update(meal);
        }
    }


    public void delete(Food meal) {
        MealViewModel mealViewModel = recyclerViewAdapter.getMealViewModel();

        mealViewModel.delete(meal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavController navController = Navigation.findNavController(MainActivity.getmInstanceActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_foodhub);
    }


    @Override
    public void onDestroy() {
        this.disposables.forEach(Disposable::dispose);
        super.onDestroy();
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