package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.ActivityCreatefoodBinding;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
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
    @Getter
    private ActivityMode mode = ActivityMode.CREATE;
    @Getter
    private Food food;


    @SneakyThrows
    protected void setEventHandlers(ActivityMode mode) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method finish = Activity.class.getMethod("finish");
        Method save = this.getClass().getMethod("save");
        Method update = this.getClass().getMethod("update", Food.class);

        if (mode == ActivityMode.CREATE) {
            eventHandlingService.onClickInvokeMethod(binding.confirmButton, this, save);
        } else {
            eventHandlingService.onClickInvokeMethod(binding.confirmButton, this, update, this.food);
        }

        eventHandlingService.onClickInvokeMethod(binding.cancelButton, this, finish);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCreatefoodBinding.inflate(getLayoutInflater());
        this.model = new ViewModelProvider(this).get(FoodViewModel.class);
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            this.mode = (ActivityMode) bundle.get("mode");
            this.food = (Food) bundle.get("item");

            Log.i("mode", this.mode.toString());
            Log.i("food", this.food.toString());
        }

        if(this.mode == ActivityMode.UPDATE) binding.deleteForeverButton.setVisibility(View.VISIBLE);
        setEventHandlers(this.mode);
    }

    public void save() {
        // TODO use setters call nutrientService
        // TODO Abstract nutrientService to efficiently produce live data to auto-fill input fields with calculated values

        Food food = new Food(
                this.binding.foodName.getText().toString(),
                Integer.parseInt(this.binding.carbs.getText().toString()),
                Integer.parseInt(this.binding.protein.getText().toString()),
                Integer.parseInt(this.binding.fat.getText().toString()),
                Integer.parseInt(this.binding.portionSize.getText().toString()),
                Integer.parseInt(this.binding.totalSize.getText().toString())
        );

        this.model.insert(food);
    }

    public void update(Food food) {
        // TODO use setters call nutrientService
        // TODO Abstract nutrientService to efficiently produce live data to auto-fill input fields with calculated values
        // TODO set hint doesnt work
        this.binding.carbs.setHint(food.getCarbsGram());
        this.binding.protein.setHint(food.getProteinGram());
        this.binding.fat.setHint(food.getFatGram());
        this.binding.portionSize.setHint(food.getPortionSize());
        this.binding.totalSize.setHint(food.getGramTotal());

        Food updatedFood = new Food(
                this.binding.foodName.getText().toString(),
                Integer.parseInt(this.binding.carbs.getText().toString()),
                Integer.parseInt(this.binding.protein.getText().toString()),
                Integer.parseInt(this.binding.fat.getText().toString()),
                Integer.parseInt(this.binding.portionSize.getText().toString()),
                Integer.parseInt(this.binding.totalSize.getText().toString())
        );

        updatedFood.setId(food.getId());
        updatedFood.setName(food.getName());

        this.model.update(updatedFood);
    }
}