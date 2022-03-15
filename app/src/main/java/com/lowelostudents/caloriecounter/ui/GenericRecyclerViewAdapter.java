package com.lowelostudents.caloriecounter.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.EventHandlingService;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;


// TODO Generify definitely.

public class GenericRecyclerViewAdapter extends RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder> {
    @Getter @Setter
    protected List<Nutrients> dataSet = new ArrayList<>();
    @Getter
    protected final List<Nutrients> allDataSet = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    protected final Context context;
    @Setter
    private ActivityMode activityMode = ActivityMode.CREATE;

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
            setEventHandlers(holder.cardItem, cardType, data, position);
        } catch (Exception e) {
            Log.e("ERROR: CLASS OR METHOD NOT FOUND", Arrays.toString(e.getStackTrace()));
        }

        if(this.activityMode == ActivityMode.CREATE)
            holder.cardToggleForDay.setColorFilter(ContextCompat.getColor(holder.cardToggleForDay.getContext(), R.color.Green));
        if(this.activityMode == ActivityMode.UPDATE) {
            holder.cardToggleForDay.setImageResource(R.drawable.ic_baseline_indeterminate_check_box_24);
            holder.cardToggleForDay.setColorFilter(ContextCompat.getColor(holder.cardToggleForDay.getContext(), R.color.DarkRed));
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

    protected void setEventHandlers(View cardItem, String cardType, Nutrients data, int position) throws Exception {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Class<?> cardDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.models.Create" + cardType);
        Class<?> nutrientDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.viewmodels." + cardType + "ViewModel");
        AndroidViewModel viewModel = (AndroidViewModel) nutrientDataClass
                .getConstructor(Application.class)
                .newInstance(cardItem.getContext().getApplicationContext());

        Method addToDay = nutrientDataClass.getMethod("addToDay", data.getClass());
        Method removeFromDay = nutrientDataClass.getMethod("removeFromDay", data.getClass());

        ImageButton button = cardItem.findViewById(R.id.toggleForDay);

        cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, cardDataClass);
                    intent.putExtra("mode", ActivityMode.UPDATE);
                intent.putExtra("item", (Serializable) dataSet.get(position));
                    context.startActivity(intent);
                }
            });

        if(this.activityMode == ActivityMode.CREATE)
            eventHandlingService.onClickInvokeMethod(button, viewModel, addToDay, data);
        if(this.activityMode == ActivityMode.UPDATE)
            eventHandlingService.onClickInvokeMethod(button, viewModel, removeFromDay, data);
    }

    public void handleDatasetChanged(final List<Nutrients> dataSet) {
        this.dataSet.clear();
        this.dataSet.addAll(dataSet);
        this.allDataSet.addAll(dataSet);

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