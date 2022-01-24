package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.Day;

import java.util.List;

import lombok.Getter;

public class DayRepo extends CRUDRepository<Day> {
    @Getter
    private final LiveData<List<Day>> days;

    DayRepo(Context context) {
        super(context);
        setCrudDao(getAppdb().dayDao());
        final Day.DayDao dayDao = (Day.DayDao) getCrudDao();
        days = dayDao.getAllObservable();
    }
}
