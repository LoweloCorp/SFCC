package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.lowelostudents.caloriecounter.models.CrudDao;
import com.lowelostudents.caloriecounter.services.NutrientService;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;


//TODO add constructor with total calories parameter
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Food {
    @PrimaryKey(autoGenerate = true)
    @EqualsAndHashCode.Include
    private long foodId;
    @EqualsAndHashCode.Include
    private String name;
    private int carbsGram, carbsCal;
    private int proteinGram, proteinCal;
    private int fatGram, fatCal;
    private int portionSize;
    private int calPerPortion;
    private double calPerGram;
    private int gramTotal;
    private int calTotal;
    @Ignore
    private NutrientService nutrientService = NutrientService.getInstance();

    public Food(String name, int carbsGramPortion, int proteinGramPortion, int fatGramPortion, int gramTotal) {
        this.name = name;
        this.carbsGram = carbsGramPortion;
        this.proteinGram = proteinGramPortion;
        this.fatGram = fatGramPortion;
        this.gramTotal = gramTotal;
        nutrientService.calcCalories(this);
    }

    public Food(String name, int gramTotal, int calPerPortion, int portionSize) {
        this.name = name;
        this.gramTotal = gramTotal;
        this.calPerPortion = calPerPortion;
        this.portionSize = portionSize;
        nutrientService.calcCalories(this);
    }

    @Ignore
    public Food(){

    }

    @Dao
    public interface FoodDao extends CrudDao<Food> {
        @Query("SELECT * FROM Food WHERE foodId = :id")
        Food getById(long id);

        @Query("SELECT * FROM Food")
        LiveData<List<Food>> getAllObservable();

        @Query("SELECT * FROM Food Where foodId = :id")
        LiveData<Food> getObservable(long id);
    }
}