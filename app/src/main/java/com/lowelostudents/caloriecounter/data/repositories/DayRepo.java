package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Day;
import com.lowelostudents.caloriecounter.models.interfaces.DayDao;
import com.lowelostudents.caloriecounter.models.relations.Day_Food_Relation;

import java.util.Calendar;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class DayRepo extends CRUDRepository<Day> {
    @Getter
    private final Observable<Day_Food_Relation> day_foods;

    public DayRepo(Context context) {
        super(context);
        DayDao dayDao = getAppdb().dayDao();
        super.setCrudDao(dayDao);
        Calendar cal = Calendar.getInstance();
        day_foods = dayDao.getObservableFoodByDate(cal.get(Calendar.DATE));
    }
}
