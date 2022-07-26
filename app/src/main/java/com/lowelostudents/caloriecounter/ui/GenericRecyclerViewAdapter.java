package com.lowelostudents.caloriecounter.ui;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.data.repositories.FoodRepo;
import com.lowelostudents.caloriecounter.data.repositories.InsertCallback;
import com.lowelostudents.caloriecounter.enums.ActivityMode;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.models.entities.Nutrients;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.viewmodels.FoodViewModel;
import com.lowelostudents.caloriecounter.ui.viewmodels.MealViewModel;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;


// TODO Generify definitely.

public class GenericRecyclerViewAdapter extends RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder> {
    @Getter()
    protected MealViewModel mealViewModel;
    @Getter()
    protected FoodViewModel foodViewModel;
    @Getter
    @Setter
    protected List<Nutrients> dataSet = new ArrayList<>();
    @Getter
    protected final List<Nutrients> allDataSet = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    protected final Context context;
    @Setter
    protected ActivityMode activityMode = ActivityMode.CREATE;

    @SneakyThrows
    public GenericRecyclerViewAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.mealViewModel = MealViewModel.class
                .getConstructor(Application.class)
                .newInstance(context.getApplicationContext());
        this.foodViewModel = FoodViewModel.class
                .getConstructor(Application.class)
                .newInstance(context.getApplicationContext());
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.foodhub_card, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Nutrients data = dataSet.get(position);
        String cardNutrients = data.getGramTotal() + "g" + " / " + (data.getCalTotal() + "cal");
        String cardType = data.getClass().getSimpleName();
        String cardTitle = data.getName();

        try {
            Log.i("CARD TYPE", cardType);
            setEventHandlers(holder.cardItem, cardType, data, position);
        } catch (Exception e) {
            Log.e("ERROR: CLASS OR METHOD NOT FOUND", Arrays.toString(e.getStackTrace()));
        }

        if (this.activityMode == ActivityMode.CREATE)
            holder.cardToggleForDay.setColorFilter(ContextCompat.getColor(holder.cardToggleForDay.getContext(), R.color.Green));
        if (this.activityMode == ActivityMode.UPDATE) {
            holder.cardToggleForDay.setImageResource(R.drawable.ic_baseline_indeterminate_check_box_24);
            holder.cardToggleForDay.setColorFilter(ContextCompat.getColor(holder.cardToggleForDay.getContext(), R.color.DarkRed));
            LinearLayout quantityLayout = holder.cardItem.findViewById(R.id.quantityLayout);
            TabLayout quantitySelect = holder.cardItem.findViewById(R.id.quantitySelect);
            quantityLayout.removeAllViews();
            quantitySelect.removeAllViews();
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

    @SuppressLint("ClickableViewAccessibility")
    protected void setEventHandlers(View cardItem, String cardType, Nutrients data, int position) throws Exception {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Class<?> cardDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.models.Create" + cardType);
        Class<?> nutrientDataClass = Class.forName("com.lowelostudents.caloriecounter.ui.viewmodels." + cardType + "ViewModel");
        AndroidViewModel viewModel = (AndroidViewModel) nutrientDataClass
                .getConstructor(Application.class)
                .newInstance(cardItem.getContext().getApplicationContext());

        Method addToDay = nutrientDataClass.getMethod("addToDay", data.getClass());
        Method removeFromDay = nutrientDataClass.getMethod("removeFromDay", data.getClass());
        Method delete = nutrientDataClass.getMethod("deleteById", long.class);

        ImageButton button = cardItem.findViewById(R.id.toggleForDay);
        TabLayout quantitySelect = cardItem.findViewById(R.id.quantitySelect);
        EditText quantity = cardItem.findViewById(R.id.quantity);

        quantity.setText(String.valueOf(data.getPortionSize()));

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
                        quantity.setText(String.valueOf(data.getPortionSize()));
                        break;
                    case 1:
                        quantity.setText(String.valueOf(data.getGramTotal()));
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
                Log.i("cardDataClass", cardDataClass.toString());
                intent.putExtra("mode", ActivityMode.UPDATE);
                intent.putExtra("item", (Serializable) dataSet.get(position));
                context.startActivity(intent);
            }
        });

        if (this.activityMode == ActivityMode.CREATE) {
            if (nutrientDataClass == FoodViewModel.class) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("CLICKED FOOD CLASS", "CLICKED FOOD CLASS");
                        Food foodAggregation = createFoodAggregation(data, quantity);
                        FoodRepo repo = foodViewModel.getRepo();

                        repo.insert(foodAggregation, new InsertCallback() {
                            @Override
                            public void onInsert(long id) {
                                foodAggregation.setId(id);
                                try {
                                    addToDay.invoke(viewModel, foodAggregation);
                                } catch (Exception e) {
                                    Log.e("Can't insert", e.toString());
                                }
                            }
                        });

                        Context context = view.getContext().getApplicationContext();
                        CharSequence methodName = "Added To Day";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, methodName, duration);
                        toast.show();
                    }
                });
            } else {
                Log.i("CLICKED NOT FOOD CLASS", nutrientDataClass.toString());
                eventHandlingService.onClickInvokeMethod(button, viewModel, addToDay, data);
            }
        }

        if (this.activityMode == ActivityMode.UPDATE) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Context context = view.getContext().getApplicationContext();
                        CharSequence methodName = "Removed from Day";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, methodName, duration);
                        toast.show();
                        removeFromDay.invoke(viewModel, data);

                        if (data.isAggregation()) {
                            Food food = (Food) data;
                            delete.invoke(viewModel, food.getId());
                        }

                        dataSet.remove(position);
                        notifyDataSetChanged();
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    protected Food createFoodAggregation(Nutrients data, EditText quantity) {
        double inputQuantity = Double.parseDouble(quantity.getText().toString());
        double multiplier = inputQuantity / data.getCarbsCal();
        double necessaryFoodInstances = Math.ceil(multiplier);
        List<Nutrients> foodList = new ArrayList<>();

        for (int i = 0; i < necessaryFoodInstances; i++) {
            foodList.add(data);
        }

        return new Food(data.getName(), foodList, multiplier);
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