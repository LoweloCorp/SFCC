package com.lowelostudents.caloriecounter.ui;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.SneakyThrows;


// TODO Generify definitely.

public class GenericRecyclerViewAdapter extends RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder> {
    protected final List<Nutrients> dataSet = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    protected final Context context;

    public GenericRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.foodhub_card, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SneakyThrows
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Nutrients data = dataSet.get(position);
        String cardNutrients = data.getGramTotal() + "g" + " / " + (data.getCalTotal() + "cal");
        String cardType = data.getClass().getSimpleName();
        String cardTitle = data.getName();

        try {
            setEventHandlers(holder.cardItem, cardType, data);
        } catch (Exception e) {
            Log.e("ERROR: CLASS OR METHOD NOT FOUND", Arrays.toString(e.getStackTrace()));
        }

        holder.cardType.setText(cardType);
        holder.cardTitle.setText(cardTitle);
        holder.cardNutrients.setText(cardNutrients);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    protected void setEventHandlers(View cardItem, String cardType, Nutrients data) throws Exception {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Class<?> cardDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.models.Create" + cardType);
        Class<?> nutrientDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.viewmodels." + cardType + "ViewModel");
        AndroidViewModel viewModel = (AndroidViewModel) nutrientDataClass
                .getConstructor(Application.class)
                .newInstance(cardItem.getContext().getApplicationContext());

        Method method = nutrientDataClass.getMethod("insert", data.getClass().getSuperclass());
        ImageButton button = cardItem.findViewById(R.id.toggleForDay);

        eventHandlingService.onClickStartActivityFromContext(cardItem, context, cardDataClass);
        eventHandlingService.onClickInvokeMethod(button, viewModel, method, data);
    }

    public void handleDatasetChanged(final List<Nutrients> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);

        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardItem;
        TextView cardTitle, cardType, cardNutrients;
        ImageButton cardToggleForDay;

        ViewHolder(View itemView) {
            super(itemView);
            cardItem = itemView.findViewById(R.id.foodItem);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            cardType = itemView.findViewById(R.id.cardType);
            cardNutrients = itemView.findViewById(R.id.cardNutrients);
            cardToggleForDay = itemView.findViewById(R.id.toggleForDay);
        }
    }

    Nutrients getItem(int id) {
        return dataSet.get(id);
    }

}