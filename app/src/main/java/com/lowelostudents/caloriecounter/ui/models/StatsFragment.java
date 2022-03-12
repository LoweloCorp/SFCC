package com.lowelostudents.caloriecounter.ui.models;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lowelostudents.caloriecounter.data.LiveDataTuplePieEntries;
import com.lowelostudents.caloriecounter.databinding.FragmentStatsBinding;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.DashboardViewModel;
import com.lowelostudents.caloriecounter.ui.viewmodels.UserViewModel;

import java.lang.reflect.Method;
import java.util.List;

import lombok.SneakyThrows;

public class StatsFragment extends Fragment {
    private FragmentStatsBinding binding;
    private LiveDataTuplePieEntries dataSet;
    private LiveData<User> user;

    @SneakyThrows
    private void setEventHandlers() {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method method = this.getClass().getMethod("handleDatasetChanged", List.class);
        Method handleUserChanged = this.getClass().getMethod("handleUserChanged", User.class);

        eventHandlingService.onChangedInvokeMethod(getViewLifecycleOwner(), this.dataSet, this, method);
        eventHandlingService.onChangedInvokeMethod(getViewLifecycleOwner(), this.user, this, handleUserChanged);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        user = userViewModel.getUser();

        this.binding = FragmentStatsBinding.inflate(inflater, container, false);
        this.dataSet = new LiveDataTuplePieEntries(dashboardViewModel.getDayMeals(), dashboardViewModel.getDayFoods());

        setEventHandlers();

        View root = binding.getRoot();

        return root;
    }

    public void handleDatasetChanged(final List<PieEntry> pieEntries) {
        PieChart barChart = binding.nutrientGauge;
        barChart.invalidate();
        PieDataSet barDataSet = new PieDataSet(pieEntries, "myDataset");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        PieData barData = new PieData(barDataSet);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.setCenterText("Nutrient Gauge");
        barChart.animate();
    }

    public void handleUserChanged(final User user) {
        Log.i("USRES", user.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}