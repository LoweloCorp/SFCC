package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.databinding.ActivityCreatefoodBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.SneakyThrows;

//TODO generify

public class CreateFood extends AppCompatActivity {
    @Getter
    private ActivityCreatefoodBinding binding;
    @Getter
    private FoodViewModel model;


    @SneakyThrows
    protected void setEventHandlers() {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method finish = Activity.class.getMethod("finish");
        Method save = this.getClass().getMethod("save");

        eventHandlingService.onClickInvokeMethod(binding.confirmButton, this, save);
        eventHandlingService.onClickInvokeMethod(binding.cancelButton, this, finish);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCreatefoodBinding.inflate(getLayoutInflater());
        this.model = new ViewModelProvider(this).get(FoodViewModel.class);
        setContentView(binding.getRoot());

        setEventHandlers();
    }

    public void save() {
        // TODO use setters call nutrientService
        // TODO Abstract nutrientService to efficiently produce live data to auto-fill input fields with calculated values

        Food food = new Food(
                this.binding.foodName.getText().toString(),
                Integer.parseInt(this.binding.carbs.getText().toString()),
                Integer.parseInt(this.binding.protein.getText().toString()),
                Integer.parseInt(this.binding.fat.getText().toString()),
                Integer.parseInt(this.binding.totalSize.getText().toString())
        );

        this.model.insert(food);
//    public Food(String name, int carbsGramPortion, int proteinGramPortion, int fatGramPortion, int gramTotal) {
//  public Food(String name, int gramTotal, int calPerPortion, int portionSi
    }
}