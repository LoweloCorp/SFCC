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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.databinding.FragmentFoodhubBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.RecyclerViewAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public class FoodhubFragment extends Fragment {
    private FragmentFoodhubBinding binding;
    private LiveData<?> dataSet;

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
        binding = FragmentFoodhubBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this.getContext());
        final RecyclerView foodList = binding.foodList;
        foodList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodList.setAdapter(recyclerViewAdapter);

        FoodViewModel foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        dataSet = foodViewModel.getFoods();

        setEventHandlers(recyclerViewAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}