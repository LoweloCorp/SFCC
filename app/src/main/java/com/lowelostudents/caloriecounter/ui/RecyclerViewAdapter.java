package com.lowelostudents.caloriecounter.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Meal;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.actions.CreateMeal;

import java.util.List;


// TODO Generify definitely.

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<?> dataSet;
    private LayoutInflater layoutInflater;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.foodhub_card, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object data = dataSet.get(position);
        Class<?> dataClass = data.getClass();

        holder.cardType.setText(dataClass.getSimpleName());

        if(dataClass == Food.class)
            init((Food) data, holder);

        if(dataClass == Meal.class)
            init((Meal) data, holder);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return (dataSet != null) ? dataSet.size() : 0;
    }

    private void init(Food food, ViewHolder holder) {
        holder.cardTitle.setText(food.getName());
        String nutrients = food.getGramTotal() + "g" + " / " + (food.getCalTotal() + "cal");
        holder.cardNutrients.setText(nutrients);
    }

    private void init(Meal meal, ViewHolder holder){
        holder.cardTitle.setText(meal.getName());
        String nutrients = meal.getGramTotal() + "g" + " / " + (meal.getCalTotal()) + "cal";
        holder.cardNutrients.setText(nutrients);
    }


    public void handleDatasetChanged(final List<?> dataSet) {
        if(this.dataSet != null) this.dataSet.clear();
        Log.i("DATSET", dataSet.toString());
        this.dataSet = dataSet;
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

            setEventHandlers(cardItem);
        }

        //TODO toggleForDay

        private void setEventHandlers(View view) {
            EventHandlingService eventHandlingService = EventHandlingService.getInstance();
            eventHandlingService.onClickStartActivityFromContext(view, context, CreateMeal.class);
        }
    }

//        // convenience method for getting data at click position
//        String getItem(int id) {
//            return mData.get(id);
//        }

}
