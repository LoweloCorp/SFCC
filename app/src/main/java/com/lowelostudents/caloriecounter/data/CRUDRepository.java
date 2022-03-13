package com.lowelostudents.caloriecounter.data;

import android.content.Context;
import android.util.Log;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import lombok.Data;

// TODO byId, Range, Refactor WeirdChamp
// TODO CHECK IF NECESSARY WHEN USING EXECUTOR IN FRONTEND
@Data
public class CRUDRepository<T> {
    protected static ExecutorService executor = Executors.newFixedThreadPool(4);
    public final AppDatabase appdb;
    private final Context context;
    private CRUDDao<T> crudDao;

    public CRUDRepository(Context context) {
        this.context = context;
        this.appdb = AppDatabase.getInMemoryInstance(context);
    }

    public Long insert(T t) {
        AtomicReference<Long> id = new AtomicReference<>();

        executor.execute(() -> {
            id.set(crudDao.insert(t));

            Log.i("ID", String.valueOf(id.get().intValue()));
        });

        return id.get();
    }

    public Long[] insert(List<T> t) {
        AtomicReference<Long[]> id = new AtomicReference<>();

        executor.execute(() -> {
            id.set(crudDao.insert(t));
            Log.i("ID", String.valueOf(id.get()[0].intValue()));
        });
        return id.get();
    }

    public List<T> get(Class<T> t) {
        return crudDao.get(t);
    }

    public List<T> getAsync(Class<T> t) {

        List<T> responseList = new ArrayList<>();
        executor.execute(() -> {
            responseList.addAll(crudDao.get(t));

        });


        return responseList;
    }

    public T get(Class<T> t, long id) {
        return crudDao.get(t, id);
    }

    public void update(T t) {
        executor.execute(() -> crudDao.update(t));
    }

    public void update(List<T> t) {
        executor.execute(() -> crudDao.update(t));
    }

    public void delete(T t) {
        executor.execute(() -> crudDao.delete(t));
    }

    public void delete(Class<T> tClass, long id) {
        executor.execute( () -> {
            crudDao.delete(tClass, id);
        });
    }

    public void delete(Class<T> tClass, String name) {
        executor.execute( () -> {
            crudDao.delete(tClass, name);
        });
    }

    public void delete(List<T> t) {
        executor.execute(() -> crudDao.delete(t));
    }

//    public List<R> getWithTransaction(Class<T> r) {
//        return crudDao.getWithTransaction(r);
//    }
//
//    public R getWithTransaction(Class<T> r, long id) {
//        return crudDao.getWithTransaction(r, id);
//    }
}
