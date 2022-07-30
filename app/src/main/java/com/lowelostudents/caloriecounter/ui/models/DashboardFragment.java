package com.lowelostudents.caloriecounter.ui.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.databinding.FragmentDashboardBinding;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.GenericRecyclerViewAdapter;
import com.lowelostudents.caloriecounter.ui.SettingsActivity;
import com.lowelostudents.caloriecounter.ui.viewmodels.DashboardViewModel;

import java.lang.reflect.Method;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.Subject;
import lombok.SneakyThrows;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private Observable<List<Food>> dataSet;

    @SneakyThrows
    private void setEventHandlers(Object recyclerViewAdapter) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method method = recyclerViewAdapter.getClass().getMethod("handleDatasetChanged", List.class);

        eventHandlingService.onChangedInvokeMethod(this.dataSet, recyclerViewAdapter, method);
        eventHandlingService.onClickStartActivityFromContext(binding.settings, this.getContext(), SettingsActivity.class);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        this.binding = FragmentDashboardBinding.inflate(inflater, container, false);

        this.dataSet = dashboardViewModel.getDayFoods().map(Day_Food_Relation::getFoods);

        final RecyclerView foodList = binding.foodList;
        final GenericRecyclerViewAdapter recyclerViewAdapter = new GenericRecyclerViewAdapter(this.getContext());
        recyclerViewAdapter.setActivityMode(ActivityMode.UPDATE);
        foodList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodList.setAdapter(recyclerViewAdapter);
        setEventHandlers(recyclerViewAdapter);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}