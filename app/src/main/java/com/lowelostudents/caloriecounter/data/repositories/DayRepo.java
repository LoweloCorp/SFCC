package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import java.util.List;

import lombok.Getter;

public class DayRepo extends CRUDRepository<Day, Day_Food_Relation> {
    @Getter
    private final LiveData<List<Day_Food_Relation>> day_foods;

    DayRepo(Context context) {
        super(context);
        super.setCrudDao(getAppdb().dayDao());
        final Day.DayDao dayDao = (Day.DayDao) super.getCrudDao();
        day_foods = dayDao.getAllObservableTransaction();
    }
}
