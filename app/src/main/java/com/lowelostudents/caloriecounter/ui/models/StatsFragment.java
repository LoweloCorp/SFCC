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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lowelostudents.caloriecounter.databinding.FragmentStatsBinding;
import com.lowelostudents.caloriecounter.ui.viewmodels.StatsViewModel;

import java.util.ArrayList;

public class StatsFragment extends Fragment {

    private StatsViewModel statsViewModel;
    private FragmentStatsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.statsViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);
        this.binding = FragmentStatsBinding.inflate(inflater, container, false);

        PieChart barChart = binding.nutrientGauge;

        ArrayList<PieEntry> dataset = new ArrayList<>();
        PieEntry pieEntry = new PieEntry(5, "fat");
        dataset.add(pieEntry);
        dataset.add(new PieEntry(5, "protein"));
        dataset.add(new PieEntry(5, "carbs"));
        dataset.add(new PieEntry(1000 - 15, "calories left"));

        Log.i("Y", Float.toString(pieEntry.getY()));

        PieDataSet barDataSet = new PieDataSet(dataset, "myDataset");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        PieData barData = new PieData(barDataSet);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.setCenterText("TESTCENTERTEXT");
        barChart.animate();


        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}