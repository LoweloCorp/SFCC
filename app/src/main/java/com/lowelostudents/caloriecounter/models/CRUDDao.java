package com.lowelostudents.caloriecounter.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.lowelostudents.caloriecounter.services.GenericQueryService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


@Dao
public interface CRUDDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long[] insert(List<T> obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(T obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHotfix(T obj);

    @Update
    void update(T obj);

    @Update
    void update(List<T> obj);

    @Delete
    void delete(T obj);

    @Delete
    void delete(List<T> obj);

    @RawQuery
    T get(SupportSQLiteQuery query);

    @RawQuery
    List<T> getAll(SupportSQLiteQuery query);



    default T get(Class<T> t, long id) {
        GenericQueryService<T> genericQueryService = new GenericQueryService<>();

        return this.get(genericQueryService.getById(t, id));
    }

    default List<T> getAll(Class<T> t) {
        GenericQueryService<T> genericQueryService = new GenericQueryService<>();
        return this.getAll(genericQueryService.getAll(t));
    }

    // TODO

//    @RawQuery
//    @Transaction
//    T getWithTransaction(SupportSQLiteQuery query);
//
//    @RawQuery
//    @Transaction
//    List<T> getAllWithTransaction(SupportSQLiteQuery query);

//    default T getWithTransaction (T t, long id) {
//        GenericQueryService<T> genericQueryService = new GenericQueryService<>();
//
//        return this.getWithTransaction(genericQueryService.getById(t, id));
//    }
//
//    default List<T> getAllWithTransaction (T t) {
//        GenericQueryService<T> genericQueryService = new GenericQueryService<>();
//
//        return this.getAllWithTransaction(genericQueryService.getAll(t));
//    }

    //    @RawQuery
//    LiveData<T> getObservable(SupportSQLiteQuery query);
//
//    @RawQuery
//    LiveData<List<T>> getAllObservable(SupportSQLiteQuery query);

//    @RawQuery
//    @Transaction
//    LiveData<T> getObservableWithTransaction(SupportSQLiteQuery query);
//
//    @RawQuery
//    @Transaction
//    LiveData<List<T>> getAllObservableWithTransaction(SupportSQLiteQuery query);
}
