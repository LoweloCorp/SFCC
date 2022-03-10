package com.lowelostudents.caloriecounter.ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        eventHandlingService.onClickStartActivityFromContext(cardItem, context, cardDataClass);
        eventHandlingService.onClickInvokeMethod(cardItem.findViewById(R.id.toggleForDay), this, method, position, cardItem);
    }

    public void updateList(int id, View cardItem) {
        Log.i("MeineID", String.valueOf(id));
        Food food = (Food) this.dataSet.get(id);
        this.mealViewModel.checkedNutrients.add(food);
        ImageButton button = cardItem.findViewById(R.id.toggleForDay);
        button.setBackgroundResource(R.drawable.ic_baseline_indeterminate_check_box_24);
    }
}