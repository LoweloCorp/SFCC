package com.lowelostudents.caloriecounter.ui.models;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.lowelostudents.caloriecounter.enums.NutrientMode;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
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
                boolean validated;

                if(TextUtils.isEmpty(binding.calPerPortion.getText())) {
                    validated = validate(NutrientMode.DEFAULT);
                } else {
                    validated = validate(NutrientMode.ALTERNATE);
                }

                if(validated) {
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
                boolean validated;

                if(TextUtils.isEmpty(binding.calPerPortion.getText())) {
                    validated = validate(NutrientMode.DEFAULT);
                } else {
                    validated = validate(NutrientMode.ALTERNATE);
                }

                if(validated) {
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

        EditText[] inputsDefault = {this.binding.foodName, this.binding.fat, this.binding.carbs, this.binding.protein, this.binding.portionSize, this.binding.totalSize, this.binding.calPerPortion, this.binding.totalCalories};

        Arrays.stream(inputsDefault).forEach(input -> {
            input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                    if (TextUtils.isEmpty(binding.calPerPortion.getText())) {
                        Nutrients nutrients = new Nutrients();
                        NutrientService nutrientService = NutrientService.getInstance();

                        nutrients.setName(binding.foodName.getText().toString());
                        if(binding.carbs.getText().toString().matches("[0-9]+")) {
                            nutrients.setCarbsGram(Integer.parseInt(binding.carbs.getText().toString()));
                        }
                        if(binding.protein.getText().toString().matches("[0-9]+")) {
                            nutrients.setProteinGram(Integer.parseInt(binding.protein.getText().toString()));
                        }
                        if(binding.fat.getText().toString().matches("[0-9]+")) {
                            nutrients.setFatGram(Integer.parseInt(binding.fat.getText().toString()));
                        }
                        if(binding.portionSize.getText().toString().matches("[0-9]+")) {
                            nutrients.setPortionSize(Integer.parseInt(binding.portionSize.getText().toString()));
                        }
                        if(binding.totalSize.getText().toString().matches("[0-9]+")){
                            nutrients.setGramTotal(Integer.parseInt(binding.totalSize.getText().toString()));
                        }
                        nutrientService.calculateNutrients(nutrients);
                        autofill(nutrients, NutrientMode.DEFAULT);
                            return true;
                    } else {
                        Nutrients nutrients = new Nutrients();
                        NutrientService nutrientService = NutrientService.getInstance();

                        nutrients.setName(binding.foodName.getText().toString());

                        // TODO validation rework validate method which returns index or identifier of whatever input is not validated
                        if(binding.totalSize.getText().toString().matches("[0-9]+")) {
                            nutrients.setGramTotal(Integer.parseInt(binding.totalSize.getText().toString()));
                        }
                        if(binding.calPerPortion.getText().toString().matches("[0-9]+")) {
                            nutrients.setCalPerPortion(Integer.parseInt(binding.calPerPortion.getText().toString()));
                        }
                        if(binding.portionSize.getText().toString().matches("[0-9]+")) {
                            nutrients.setPortionSize(Integer.parseInt(binding.portionSize.getText().toString()));
                        }

                        nutrientService.calculateNutrients(nutrients);
                        autofill(nutrients, NutrientMode.ALTERNATE);
                            return true;
                    }
                }
            });
        });

        if (bundle != null) {
            this.mode = (ActivityMode) bundle.get("mode");
            this.food = (Food) bundle.get("item");
            this.autofill(this.food, NutrientMode.DEFAULT);
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
                Integer.parseInt(this.binding.carbs.getText().toString()),
                Integer.parseInt(this.binding.protein.getText().toString()),
                Integer.parseInt(this.binding.fat.getText().toString()),
                Integer.parseInt(this.binding.portionSize.getText().toString()),
                Integer.parseInt(this.binding.totalSize.getText().toString())
        );

        nutrientService.calculateNutrients(food);

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

        nutrientService.calculateNutrients(updatedFood);

        this.model.update(updatedFood);
    }

    public void delete(Food food) {
        this.model.delete(food);
    }

    private void autofill(Nutrients food, NutrientMode mode) {
        if (mode == NutrientMode.DEFAULT) {
            this.binding.foodName.setText(food.getName());

            this.binding.calPerPortion.setFocusable(false);
            this.binding.calPerPortion.setEnabled(false);
            this.binding.totalCalories.setFocusable(false);
            this.binding.totalCalories.setEnabled(false);
            this.binding.calPerPortion.setHint(String.valueOf(food.getCalPerPortion()));
            this.binding.totalCalories.setHint(String.valueOf(food.getCalTotal()));
        } else {
            this.binding.foodName.setText(food.getName());

            this.binding.carbs.setFocusable(false);
            this.binding.carbs.setEnabled(false);
            this.binding.protein.setFocusable(false);
            this.binding.protein.setEnabled(false);
            this.binding.fat.setFocusable(false);
            this.binding.fat.setEnabled(false);
            this.binding.totalCalories.setFocusable(false);
            this.binding.totalCalories.setEnabled(false);
            this.binding.carbs.setHint(String.valueOf(food.getCarbsGram()));
            this.binding.protein.setHint(String.valueOf(food.getProteinGram()));
            this.binding.fat.setHint(String.valueOf(food.getFatGram()));
            this.binding.totalCalories.setHint(String.valueOf(food.getCalTotal()));
        }
    }

    private boolean validate(NutrientMode mode) {
        boolean validated = true;
        if(!this.binding.foodName.getText().toString().matches("[a-zA-Z]+")) {
            this.binding.foodName.setError("Please enter name with characters A-Z a-z");
            validated = false;
        }
        if (mode == NutrientMode.DEFAULT) {
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
        } else {
            this.binding.carbs.setError(null);
            this.binding.protein.setError(null);
            this.binding.fat.setError(null);
            this.binding.portionSize.setError(null);
            this.binding.totalSize.setError(null);
            if (!this.binding.totalSize.getText().toString().matches("[0-9]+")) {
                this.binding.totalSize.setError("Please enter whole number");
                validated = false;
            }
            if (!this.binding.calPerPortion.getText().toString().matches("[0-9]+")) {
                this.binding.calPerPortion.setError("Please enter cal whole number");
                validated = false;
            }
            if (!this.binding.portionSize.getText().toString().matches("[0-9]+")) {
                this.binding.portionSize.setError("Please enter whole number");
                validated = false;
            }
        }
        return validated;
    }
}