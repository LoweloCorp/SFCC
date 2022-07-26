package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.relations.Meal_Food_Relation;
import com.lowelostudents.caloriecounter.services.NutrientService;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

// TODO later can deprecate because of foot aggregation/Nutrientaggregation
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Meal extends Nutrients implements Serializable {
    @Ignore
    private static final long serialVersionUID = 1L;

    @PrimaryKey(autoGenerate = true)
    @EqualsAndHashCode.Include
    protected long id;
    @Ignore
    private transient NutrientService nutrientService = NutrientService.getInstance();

    @Ignore
    public Meal() {
    }

    public Meal(String name, List<Food> foodList) {
        this.name = name;
        nutrientService.combineNutrients(this, foodList);
    }

    public Meal(String name) {
        this.name = name;
    }
}
