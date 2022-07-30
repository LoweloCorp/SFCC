package com.lowelostudents.caloriecounter.ui.models;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lowelostudents.caloriecounter.databinding.FragmentStatsBinding;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.services.ChartFactory;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.services.NutrientService;
import com.lowelostudents.caloriecounter.ui.viewmodels.DashboardViewModel;
import com.lowelostudents.caloriecounter.ui.viewmodels.UserViewModel;

import java.lang.reflect.Method;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import lombok.SneakyThrows;

public class StatsFragment extends Fragment {
    private FragmentStatsBinding binding;
    private Observable<List<PieEntry>> dataSet;
    private User user;
    private PieChart barChart;
    @SneakyThrows
    private void setEventHandlers() {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method method = this.getClass().getMethod("handleDatasetChanged", List.class);

        eventHandlingService.onChangedInvokeMethod(this.dataSet, this, method);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        user = userViewModel.getUser().blockingFirst();

        this.binding = FragmentStatsBinding.inflate(inflater, container, false);

        dashboardViewModel.getDayFoods().take(1).subscribe( dayFoods -> {
            ChartFactory chartFactory = ChartFactory.getInstance();
            NutrientService nutrientService = NutrientService.getInstance();
            Nutrients nutrients = nutrientService.combineNutrients(dayFoods.getFoods());
            List<PieEntry> pieEntries = chartFactory.generatePieEntries(nutrients, this.user);
            // TODO if this works maybe can use elsewhere instead of subject
            this.dataSet = Observable.fromIterable(pieEntries).toList().toObservable();
        });

        barChart = binding.nutrientGauge;
        barChart.getDescription().setEnabled(false);
        barChart.setCenterText("Nutrient Gauge");
        barChart.setEntryLabelColor(Color.BLACK);
        barChart.setExtraBottomOffset(30f);
        barChart.setExtraLeftOffset(38f);
        barChart.setExtraRightOffset(38f);
        //legend attributes
        // TODO legend not centered for whatever reason
        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setFormSize(20);
        legend.setFormToTextSpace(4);
        legend.setYOffset(30);
        legend.setXOffset(-5);
        //to wrap legend text
//        legend.setWordWrapEnabled(true);
        Log.d("legend " ,legend.getEntries().toString());
        barChart.animate();
        setEventHandlers();

        View root = binding.getRoot();

        return root;
    }

    public void handleDatasetChanged(final List<PieEntry> pieEntries) {
        barChart.invalidate();
        PieDataSet barDataSet = new PieDataSet(pieEntries, null);
        barDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        PieData barData = new PieData(barDataSet);

        barChart.setData(barData);

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