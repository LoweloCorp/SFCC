package com.lowelostudents.caloriecounter.models.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.services.NutrientService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;


//TODO add constructor with total calories parameter
@Entity(foreignKeys = {
        @ForeignKey(
                entity = Day.class,
                parentColumns = "id",
                childColumns = "dayId",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Food.class,
                parentColumns = "id",
                childColumns = "mealId",
                onDelete = CASCADE
        )
}, indices = {@Index("dayId"), @Index("mealId")})

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Food extends Nutrients implements Serializable {
    @Ignore
    private static final long serialVersionUID = 1L;
    @Ignore
    private transient NutrientService nutrientService = NutrientService.getInstance();
    private Long dayId;
    private UUID mealId;
    private AggregationType aggregationType;


    // TODO NEXT gramTotal Nullable
    public Food(String name, double carbsGramPortion, double proteinGramPortion, double fatGramPortion, double portionSize, double gramTotal) {
        this.dayId = null;
        this.mealId = null;
        this.aggregationType = AggregationType.FOOD;
        this.id = UUID.randomUUID();
        this.name = name;
        this.carbsGram = carbsGramPortion;
        this.proteinGram = proteinGramPortion;
        this.fatGram = fatGramPortion;

        this.portionSize = portionSize;
        this.gramTotal = gramTotal;
    }

    public Food(String name, List<Food> foodList, double multiplier, AggregationType aggregationType) {
        this.dayId = null;
        this.mealId = null;
        this.id = UUID.randomUUID();
        this.isAggregation = true;
        this.aggregationType = aggregationType;
        this.name = name;
        nutrientService.combineNutrients(this, foodList);
        nutrientService.applyMultiplier(this, multiplier);
    }

    public Food() {
        this.dayId = null;
        this.mealId = null;
        this.id = UUID.randomUUID();
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf("Carbs: " + this.getCarbsCal())+ " Protein: " + this.getProteinCal() + " Fat: " + this.getFatCal() + " CalTotal: " + this.getCalTotal() + " Weight: " + this.getGramTotal();
    }
}
