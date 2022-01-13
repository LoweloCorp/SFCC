package com.lowelostudents.caloriecounter.tasks;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.models.Day;
import com.lowelostudents.caloriecounter.models.daos.DayDao;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DataPopulationTask extends Worker {

    public DataPopulationTask(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppDatabase appdb = AppDatabase.getInMemoryInstance(getApplicationContext());
        DayDao dayDao = appdb.dayDao();
        Calendar cal = Calendar.getInstance();

        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            Calendar localCal = Calendar.getInstance();
            localCal.roll(Calendar.DATE, -8);
            dayDao.deleteSpecific(localCal.get(Calendar.DATE));
        }

        if(dayDao.getLatest() == null || cal.get(Calendar.DATE) != dayDao.getLatest().getDayId()){
            Day day = new Day();

            dayDao.insertAll(day);
        } else {
            int delay = 24 - cal.get(Calendar.HOUR_OF_DAY);
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(DataPopulationTask.class, 1440, TimeUnit.MINUTES)
                    .setInitialDelay(5, TimeUnit.HOURS)
                    .build();

            WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        }
        return Result.success();
    }
}
