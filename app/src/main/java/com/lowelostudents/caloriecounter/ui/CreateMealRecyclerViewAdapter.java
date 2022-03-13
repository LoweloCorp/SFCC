package com.lowelostudents.caloriecounter.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import lombok.Getter;
import lombok.SneakyThrows;

public class CreateMealRecyclerViewAdapter extends GenericRecyclerViewAdapter {
    @Getter()
    private MealViewModel mealViewModel;

    public CreateMealRecyclerViewAdapter(Context context) {
        super(context);
    }

    @SneakyThrows
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Nutrients data = dataSet.get(position);
        String cardNutrients = data.getGramTotal() + "g" + " / " + (data.getCalTotal() + "cal");
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

    protected void setEventHandlers(View cardItem, String cardType, Nutrients data, int position) throws Exception {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Class<?> cardDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.models.Create" + cardType);
        Class<?> nutrientDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.viewmodels." + cardType + "ViewModel");
        this.mealViewModel = MealViewModel.class
                .getConstructor(Application.class)
                .newInstance(cardItem.getContext().getApplicationContext());

        Method method = this.getClass().getMethod("updateList", int.class, View.class);

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

    public void updateList(int id, View cardItem) {
        Log.i("MeineID", String.valueOf(id));
        ImageButton button = cardItem.findViewById(R.id.toggleForDay);

        try {
            if (this.mealViewModel.checkedNutrients.get(id) == null) {
                Food food = (Food) this.dataSet.get(id);
                this.mealViewModel.checkedNutrients.put(id, food);
                button.setImageResource(R.drawable.ic_baseline_indeterminate_check_box_24);
                button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.DarkRed));
            } else {
                this.mealViewModel.checkedNutrients.remove(id);
                button.setImageResource(R.drawable.ic_baseline_add_box_24);
                button.setColorFilter(ContextCompat.getColor(button.getContext(), R.color.Green));
            }
        } catch (IndexOutOfBoundsException e) {
            Log.w("Index out of Bounds", e.getCause());
        }
    }
}
