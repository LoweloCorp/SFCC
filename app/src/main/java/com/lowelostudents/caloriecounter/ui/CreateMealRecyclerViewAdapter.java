package com.lowelostudents.caloriecounter.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

import com.google.android.material.tabs.TabLayout;
import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.EventHandlingService;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

public class CreateMealRecyclerViewAdapter extends GenericRecyclerViewAdapter {


    public CreateMealRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Nutrients data = dataSet.get(position);
        String cardNutrients = String.format(Locale.ENGLISH, "%.2f", data.getGramTotal()) + "g" + " / " + (String.format(Locale.ENGLISH, "%.2f", data.getCalTotal()) + "cal");
        String cardType = data.getClass().getSimpleName();
        String cardTitle = data.getName();

        try {
            setEventHandlers(holder.cardItem, cardType, data, position);
        } catch (Exception e) {
            Log.e("ERROR: CLASS OR METHOD NOT FOUND", Arrays.toString(e.getStackTrace()));
        }

        holder.cardToggleForDay.setColorFilter(ContextCompat.getColor(holder.cardToggleForDay.getContext(), R.color.Green));
        holder.cardType.setText(cardType);
        holder.cardTitle.setText(cardTitle);
        holder.cardNutrients.setText(cardNutrients);
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void setEventHandlers(View cardItem, String cardType, Nutrients data, int position) throws Exception {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Class<?> cardDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.models.Create" + cardType);

        Method method = this.getClass().getMethod("updateList", int.class, View.class);

        TabLayout quantitySelect = cardItem.findViewById(R.id.quantitySelect);
        EditText quantity = cardItem.findViewById(R.id.quantity);

        quantity.setText(String.format(Locale.ENGLISH, "%.2f", data.getPortionSize()));


        quantity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                quantitySelect.selectTab(quantitySelect.getTabAt(2));
                return false;
            }
        });

        quantitySelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                quantity.clearFocus();
                switch (tab.getPosition()) {
                    case 0:
                        quantity.setText(String.format(Locale.ENGLISH, "%.2f", data.getPortionSize()));
                        break;
                    case 1:
                        quantity.setText(String.format(Locale.ENGLISH, "%.2f", data.getGramTotal()));
                        break;
                    case 2:
                        quantity.requestFocus();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, cardDataClass);
                intent.putExtra("mode", ActivityMode.UPDATE);
                intent.putExtra("item", (Serializable) dataSet.get(position));
                context.startActivity(intent);
            }
        });

        eventHandlingService.onClickInvokeMethod(cardItem.findViewById(R.id.toggleForDay), this, method, position, cardItem);
    }

    public void updateList(int position, View cardItem) {
        ImageButton button = cardItem.findViewById(R.id.toggleForDay);
        EditText quantity = cardItem.findViewById(R.id.quantity);
        Food foodAtPosition = this.mealViewModel.checkedFoods.get(position);

        try {
            if (foodAtPosition == null) {
                Food nutrients = this.dataSet.get(position);
                double quantityValue = Double.parseDouble(quantity.getText().toString());
                Food foodAggregation = nutrientService.createFoodAggregation(nutrients, quantityValue, AggregationType.FOOD);
                mealViewModel.checkedFoods.put(position, foodAggregation);
                button.setImageResource(R.drawable.ic_baseline_indeterminate_check_box_24);
                button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.DarkRed));
            } else {
                this.mealViewModel.checkedFoods.remove(position);
                button.setImageResource(R.drawable.ic_baseline_add_box_24);
                button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.Green));
            }
        } catch (IndexOutOfBoundsException e) {
            Log.w("Index out of Bounds", e.getCause());
        }
    }
}
