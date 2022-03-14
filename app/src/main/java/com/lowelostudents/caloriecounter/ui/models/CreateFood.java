package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.ActivityCreatefoodBinding;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.enums.NutrientMode;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;

import java.lang.reflect.Method;
import java.util.Arrays;

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
        Method delete = this.getClass().getMethod("delete", Food.class);

        if (mode == ActivityMode.CREATE) {
            eventHandlingService.onClickInvokeMethod(binding.confirmButton, this, save);
        } else {
            eventHandlingService.onClickInvokeMethod(binding.deleteForeverButton, this, delete, this.food);
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

        EditText[] inputsDefault = {this.binding.foodName, this.binding.fat, this.binding.carbs, this.binding.protein, this.binding.portionSize, this.binding.totalSize, this.binding.calPerPortion, this.binding.totalCalories};

        Arrays.stream(inputsDefault).forEach(input -> {
            input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                    if (TextUtils.isEmpty(binding.calPerPortion.getText())) {
                        if (validate(NutrientMode.DEFAULT)) {
                            Food food = new Food(
                                    binding.foodName.getText().toString(),
                                    Integer.parseInt(binding.carbs.getText().toString()),
                                    Integer.parseInt(binding.protein.getText().toString()),
                                    Integer.parseInt(binding.fat.getText().toString()),
                                    Integer.parseInt(binding.portionSize.getText().toString()),
                                    Integer.parseInt(binding.totalSize.getText().toString())
                            );
                            autofill(food);
                            return true;
                        }
                    } else {
                        if (validate(NutrientMode.ALTERNATE)) {
                            Food food = new Food(
                                    binding.foodName.getText().toString(),
                                    Integer.parseInt(binding.totalSize.getText().toString()),
                                    Integer.parseInt(binding.calPerPortion.getText().toString()),
                                    Integer.parseInt(binding.portionSize.getText().toString())
                            );

                            autofill(food);
                            return true;
                        }
                    }

                    return false;
                }
            });
        });

        if (bundle != null) {
            this.mode = (ActivityMode) bundle.get("mode");
            this.food = (Food) bundle.get("item");
            this.autofill(this.food);
        }

        if (this.mode == ActivityMode.UPDATE)
            binding.deleteForeverButton.setVisibility(View.VISIBLE);
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
        Food updatedFood = new Food(
                this.binding.foodName.getText().toString(),
                Integer.parseInt(this.binding.carbs.getText().toString()),
                Integer.parseInt(this.binding.protein.getText().toString()),
                Integer.parseInt(this.binding.fat.getText().toString()),
                Integer.parseInt(this.binding.portionSize.getText().toString()),
                Integer.parseInt(this.binding.totalSize.getText().toString())
        );

        Log.i("foodName", String.valueOf(food.getCarbsGram()));
        updatedFood.setId(food.getId());
        Log.i("FoodID", String.valueOf(updatedFood.getId()));

        this.model.update(updatedFood);
    }

    public void delete(Food food) {
        this.model.delete(food);
    }

    // TODO set hint for critical values based on Nutrient mode & disable input
    private void autofill(Food food) {
        this.binding.carbs.setText(String.valueOf(food.getCarbsGram()));
        this.binding.protein.setText(String.valueOf(food.getProteinGram()));
        this.binding.fat.setText(String.valueOf(food.getFatGram()));
        this.binding.portionSize.setText(String.valueOf(food.getPortionSize()));
        this.binding.totalSize.setText(String.valueOf(food.getGramTotal()));
        this.binding.calPerPortion.setText(String.valueOf(food.getCalPerPortion()));
        this.binding.totalCalories.setText(String.valueOf(food.getCalTotal()));
    }

    private boolean validate(NutrientMode mode) {
        boolean validated = true;
        if(TextUtils.isEmpty(this.binding.foodName.getText())) {
            this.binding.foodName.setError("Please enter name");
            validated = false;
        }
        if (mode == NutrientMode.DEFAULT) {
            this.binding.totalSize.setError(null);
            this.binding.calPerPortion.setError(null);
            this.binding.portionSize.setError(null);
            if (TextUtils.isEmpty(this.binding.carbs.getText())) {
                this.binding.carbs.setError("Please enter carbs");
                validated = false;
            }
            if (TextUtils.isEmpty(this.binding.protein.getText())) {
                this.binding.protein.setError("Please enter protein");
                validated = false;
            }
            if (TextUtils.isEmpty(this.binding.fat.getText())) {
                this.binding.fat.setError("Please enter fat");
                validated = false;
            }
            if (TextUtils.isEmpty(this.binding.portionSize.getText())) {
                this.binding.portionSize.setError("Please enter portion size");
                validated = false;
            }
            if (TextUtils.isEmpty(this.binding.totalSize.getText())) {
                this.binding.totalSize.setError("Please enter total size");
                validated = false;
            }
        } else {
            this.binding.carbs.setError(null);
            this.binding.protein.setError(null);
            this.binding.fat.setError(null);
            this.binding.portionSize.setError(null);
            this.binding.totalSize.setError(null);
            if (TextUtils.isEmpty(this.binding.totalSize.getText())) {
                this.binding.totalSize.setError("Please enter total size");
                validated = false;
            }
            if (TextUtils.isEmpty(this.binding.calPerPortion.getText())) {
                this.binding.calPerPortion.setError("Please enter cal per portion");
                validated = false;
            }
            if (TextUtils.isEmpty(this.binding.portionSize.getText())) {
                this.binding.portionSize.setError("Please enter portion size");
                validated = false;
            }
        }
        return validated;
    }
}