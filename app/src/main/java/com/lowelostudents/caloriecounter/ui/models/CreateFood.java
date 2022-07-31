package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.ActivityCreatefoodBinding;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.services.NutrientService;
import com.lowelostudents.caloriecounter.services.OpenFoodFactsService;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;

import java.lang.reflect.Method;
import java.util.Arrays;

import lombok.Getter;
import lombok.SneakyThrows;

public class CreateFood extends AppCompatActivity {
    @Getter
    private ActivityCreatefoodBinding binding;
    @Getter
    private FoodViewModel model;
    @Getter
    private ActivityMode mode = ActivityMode.CREATE;
    @Getter
    private Food food;
    NutrientService nutrientService = NutrientService.getInstance();

    @SneakyThrows
    protected void setEventHandlers(ActivityMode mode) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method finish = Activity.class.getMethod("finish");
        Context context = getApplicationContext();
        int toastDuration = Toast.LENGTH_SHORT;

        if (mode == ActivityMode.CREATE) {
            binding.confirmButton.setOnClickListener(view -> {
                if(validate()) {
                    save();
                    CharSequence info = "Food saved";
                    Toast toast = Toast.makeText(context, info, toastDuration);
                    toast.show();
                    finish();
                }
            });
        } else {
            binding.deleteForeverButton.setOnClickListener( view -> {
                delete(this.food);
                CharSequence info = "Food deleted";
                Toast toast = Toast.makeText(context, info, toastDuration);
                toast.show();
                finish();
            });
            binding.confirmButton.setOnClickListener(view -> {
                if(validate()) {
                    update(this.food);
                    CharSequence info = "Food saved";
                    Toast toast = Toast.makeText(context, info, toastDuration);
                    toast.show();
                    finish();
                }
            });
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

        EditText[] inputsDefault = {this.binding.foodName, this.binding.fat, this.binding.carbs, this.binding.protein, this.binding.portionSize, this.binding.totalSize};

        Arrays.stream(inputsDefault).forEach(input -> {
            input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                        Food nutrients = new Food();
                        NutrientService nutrientService = NutrientService.getInstance();

                        // TODO rework maybe additional constructor similar to Object.assign
                        nutrients.setName(binding.foodName.getText().toString());
                        if(binding.carbs.getText().toString().matches("[0-9]+")) {
                            nutrients.setCarbsGram(Double.parseDouble(binding.carbs.getText().toString()));
                        }
                        if(binding.protein.getText().toString().matches("[0-9]+")) {
                            nutrients.setProteinGram(Double.parseDouble(binding.protein.getText().toString()));
                        }
                        if(binding.fat.getText().toString().matches("[0-9]+")) {
                            nutrients.setFatGram(Double.parseDouble(binding.fat.getText().toString()));
                        }
                        if(binding.portionSize.getText().toString().matches("[0-9]+")) {
                            nutrients.setPortionSize(Double.parseDouble(binding.portionSize.getText().toString()));
                        }
                        if(binding.totalSize.getText().toString().matches("[0-9]+")){
                            nutrients.setGramTotal(Double.parseDouble(binding.totalSize.getText().toString()));
                        }
                        nutrientService.calculateNutrients(nutrients);
                        autofill(nutrients, true);
                            return true;
                }
            });
        });

        if (bundle != null) {
            this.mode = (ActivityMode) bundle.get("mode");
            this.food = (Food) bundle.get("item");
            this.autofill(this.food, false);
        }

        if (this.mode == ActivityMode.UPDATE)
            binding.deleteForeverButton.setVisibility(View.VISIBLE);

        setEventHandlers(this.mode);
    }

    public void save() {

        OpenFoodFactsService openFoodFactsService = OpenFoodFactsService.getInstance();

        openFoodFactsService.getProduct(this.model.getRepo(), 20917289);

        Food food = new Food(
                this.binding.foodName.getText().toString(),
                Double.parseDouble(this.binding.carbs.getText().toString()),
                Double.parseDouble(this.binding.protein.getText().toString()),
                Double.parseDouble(this.binding.fat.getText().toString()),
                Double.parseDouble(this.binding.portionSize.getText().toString()),
                Double.parseDouble(this.binding.totalSize.getText().toString())
        );

        nutrientService.calculateNutrients(food);

        this.model.insert(food);
    }

    public void update(Food food) {
        Food updatedFood = new Food(
                this.binding.foodName.getText().toString(),
                Double.parseDouble(this.binding.carbs.getText().toString()),
                Double.parseDouble(this.binding.protein.getText().toString()),
                Double.parseDouble(this.binding.fat.getText().toString()),
                Double.parseDouble(this.binding.portionSize.getText().toString()),
                Double.parseDouble(this.binding.totalSize.getText().toString())
        );

        updatedFood.setId(food.getId());

        nutrientService.calculateNutrients(updatedFood);

        this.model.update(updatedFood);
    }

    public void delete(Food food) {
        this.model.delete(food);
    }

    private void autofill(Food food, boolean editing) {
        if(editing) {
            this.binding.totalCalories.setText(String.valueOf(food.getCalTotal()));
            this.binding.calPerPortion.setText(String.valueOf(food.getCalPerPortion()));
        } else {
            this.binding.foodName.setText(food.getName());
            this.binding.fat.setText(String.valueOf(food.getFatCal()));
            this.binding.carbs.setText(String.valueOf(food.getProteinCal()));
            this.binding.protein.setText(String.valueOf(food.getProteinCal()));
            this.binding.portionSize.setText(String.valueOf(food.getPortionSize()));
            this.binding.totalSize.setText(String.valueOf(food.getGramTotal()));

        }
    }

    private boolean validate() {
        boolean validated = true;
        if(!this.binding.foodName.getText().toString().matches("[a-zA-Z]+")) {
            this.binding.foodName.setError("Please enter name with characters A-Z a-z");
            validated = false;
        }
            this.binding.totalSize.setError(null);
            this.binding.calPerPortion.setError(null);
            this.binding.portionSize.setError(null);
            if (!this.binding.carbs.getText().toString().matches("[0-9]+")) {
                this.binding.carbs.setError("Please enter whole number");
                validated = false;
            }
            if (!this.binding.protein.getText().toString().matches("[0-9]+")) {
                this.binding.protein.setError("Please enter whole number");
                validated = false;
            }
            if (!this.binding.fat.getText().toString().matches("[0-9]+")) {
                this.binding.fat.setError("Please enter whole number");
                validated = false;
            }
            if (!this.binding.portionSize.getText().toString().matches("[0-9]+")) {
                this.binding.portionSize.setError("Please enter whole number");
                validated = false;
            }
            if (!this.binding.totalSize.getText().toString().matches("[0-9]+")) {
                this.binding.totalSize.setError("Please enter whole number");
                validated = false;
            }

        return validated;
    }
}