package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.lowelostudents.caloriecounter.data.repositories.DayRepo;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class DashboardViewModel extends AndroidViewModel {
    @Getter
    private final Observable<Day_Food_Relation> dayFoods;

    public DashboardViewModel(Application context) {
        super(context);
        DayRepo repo = new DayRepo(context.getApplicationContext());
        dayFoods = repo.getDay_foods();
    }
}