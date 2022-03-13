package com.lowelostudents.caloriecounter.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.github.mikephil.charting.data.PieEntry;
import com.lowelostudents.caloriecounter.data.repositories.UserRepo;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.services.ChartFactory;
import com.lowelostudents.caloriecounter.services.NutrientService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO extend LiveDataTuple
public class LiveDataTuplePieEntries extends MediatorLiveData<List<PieEntry>> {
    private List<Meal> listOne = Collections.emptyList();
    private List<Food> listTwo = Collections.emptyList();
    private User user;
    public LiveDataTuplePieEntries(LiveData<List<Meal>> liveListOne, LiveData<List<Food>> liveListTwo, User user) {
        this.user = user;

        addSource(liveListOne, listOne -> {
            if (listOne != null) {
                this.listOne = listOne;
            }
            setValue(combinedList(listOne, this.listTwo));
        });

        addSource(liveListTwo, listTwo -> {
            if (listTwo != null) {
                this.listTwo = listTwo;
            }

            setValue(combinedList(this.listOne, listTwo));
        });
    }

    private List<PieEntry> combinedList(List<Meal> listOne, List<Food> listTwo) {
        List<Nutrients> combinedList = new ArrayList<>();
        ChartFactory chartFactory = ChartFactory.getInstance();
        NutrientService nutrientService = NutrientService.getInstance();

        combinedList.addAll(listOne);
        combinedList.addAll(listTwo);

        Nutrients nutrients = nutrientService.combineNutrients(combinedList);

        return chartFactory.generatePieEntries(nutrients, this.user);
    }
}