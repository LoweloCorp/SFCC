package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.repositories.FoodRepo;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.ui.CRUDViewModel;

import java.util.List;

import lombok.Getter;

public class FoodViewModel extends CRUDViewModel<Food> {
    @Getter
    private LiveData<List<Food>> foods;

    public FoodViewModel(Application context) {
        super(context);
        setCrudRepository(new FoodRepo(context.getApplicationContext()));
        final FoodRepo foodRepo = (FoodRepo) getCrudRepository();
        foods = foodRepo.getFood();
    }
}