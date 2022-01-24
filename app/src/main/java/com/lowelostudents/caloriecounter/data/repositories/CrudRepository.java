package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;
import android.util.Log;

import com.lowelostudents.caloriecounter.data.AppDatabase;
import com.lowelostudents.caloriecounter.models.CrudDao;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Data;

@Data
public class CrudRepository<T> {
    public final AppDatabase appdb;
    private final Context context;
    private CrudDao<T> crudDao;
    private static ExecutorService executor = Executors.newFixedThreadPool(4);

    public CrudRepository(Context context) {
        this.context = context;
        this.appdb = AppDatabase.getInMemoryInstance(context);
    }

    public CrudRepository(Context context, CrudDao<T> crudDao) {
        this.context = context;
        this.appdb = AppDatabase.getInMemoryInstance(context);
        this.crudDao = crudDao;
    }

    public void insert(T t) {
        executor.execute(() -> {
            final Long id = crudDao.insert(t);

            Log.i("ID", String.valueOf(id.intValue()));
        });
    }

    public void insertAll(List<T> t){
        executor.execute(() -> {
            final Long[] id = crudDao.insert(t);

            Log.i("ID", Arrays.stream(id).toString());
        });

    }

    public void update(T t){
        executor.execute(() -> crudDao.update(t));
    }

    public void updateAll(List<T> t){
        executor.execute(() -> crudDao.update(t));
    }

    public void delete(T t) {
        executor.execute(() -> crudDao.delete(t));
    }

    public void delete(List<T> t) {
        executor.execute(() -> crudDao.delete(t));
    }
}
