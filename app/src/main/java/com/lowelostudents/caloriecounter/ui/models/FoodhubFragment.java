package com.lowelostudents.caloriecounter.ui.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.MainActivity;
import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.databinding.FragmentFoodhubBinding;
import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.services.FilterService;
import com.lowelostudents.caloriecounter.ui.GenericRecyclerViewAdapter;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.SneakyThrows;

public class FoodhubFragment extends Fragment {
    private FragmentFoodhubBinding binding;
    private Observable<List<Food>> dataSet;
    private boolean mealChecked = true;
    private boolean foodChecked = true;
    private GenericRecyclerViewAdapter recyclerViewAdapter;
    FilterService filterService = FilterService.getInstance();
    private final ArrayList<Disposable> disposables = new ArrayList<>();

    @SneakyThrows
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private void setEventHandlers(GenericRecyclerViewAdapter recyclerViewAdapter, Switch foodToggle, Switch mealToggle) {
        Disposable disposable = this.dataSet.observeOn(AndroidSchedulers.mainThread()).subscribe(dataSet -> {
            if (!foodToggle.isChecked())
                foodToggle.toggle();
            if (!mealToggle.isChecked())
                mealToggle.toggle();
            this.recyclerViewAdapter.handleDatasetChanged(dataSet);
        });

        this.disposables.add(disposable);
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

        this.dataSet = foodViewModel.getMealsAndFoods();

        recyclerViewAdapter = new GenericRecyclerViewAdapter(this.getContext());
        final RecyclerView foodList = binding.foodList;
        foodList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodList.setAdapter(recyclerViewAdapter);

        setEventHandlers(recyclerViewAdapter, binding.foodToggle, binding.mealToggle);

        this.binding.foodToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                foodChecked = b;
                List<Food> allData = recyclerViewAdapter.getAllDataSet();
                recyclerViewAdapter.setDataSet(filterService.filterListByToggle(allData, foodChecked, mealChecked, AggregationType.FOOD));

                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        this.binding.mealToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                List<Food> allData = recyclerViewAdapter.getAllDataSet();
                mealChecked = b;
                recyclerViewAdapter.setDataSet(filterService.filterListByToggle(allData, mealChecked, foodChecked, AggregationType.MEAL));

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
                recyclerViewAdapter.setDataSet(filterService.filterListByLevenshtein(recyclerViewAdapter.getDataSet(), s));
                recyclerViewAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        this.disposables.forEach(Disposable::dispose);
        super.onDestroyView();
        binding = null;
    }
}