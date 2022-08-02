package com.lowelostudents.caloriecounter.models.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Food implements Serializable {
    @Ignore
    private static final long serialVersionUID = 1L;
    @Ignore
    private transient NutrientService nutrientService = NutrientService.getInstance();
    private Long dayId;
    private UUID mealId;
    private AggregationType aggregationType;
    protected boolean isAggregation = false;
    @PrimaryKey()
    @EqualsAndHashCode.Include
    @NonNull
    protected UUID id;
    @EqualsAndHashCode.Include
    protected String name;
    protected double calTotal = 0;
    // TODO actual quantity
    protected double gramTotal = 0;
    @SerializedName("carbohydrates_serving")
    // TODO rename grams to gramPortion
    protected double carbsGram = 0;
    @SerializedName("fat_serving")
    protected double fatGram = 0;
    @SerializedName("proteins_serving")
    protected double proteinGram = 0;

    protected double carbsCal = 0;
    protected double proteinCal = 0;
    protected double fatCal = 0;

    protected double portionSize = 0;
    protected double calPerPortion = 0;
    protected double calPerGram = 0;




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
}
