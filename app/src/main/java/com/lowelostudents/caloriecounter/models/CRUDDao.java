package com.lowelostudents.caloriecounter.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.lowelostudents.caloriecounter.services.GenericQueryService;

import java.util.List;

// TODO byId, Range
@Dao
public abstract class CRUDDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long[] insert(List<T> obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long insert(T obj);

    // TODO FIX REMOVE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertHotfix(T obj);

    @RawQuery
    protected abstract T get(SupportSQLiteQuery query);

    @RawQuery
    protected abstract List<T> getAll(SupportSQLiteQuery query);

    public T get(Class<T> t, long id) {
        GenericQueryService<T> genericQueryService = new GenericQueryService<>();

        return this.get(genericQueryService.getById(t, id));
    }

    public List<T> get(Class<T> t) {
        GenericQueryService<T> genericQueryService = new GenericQueryService<>();
        return this.getAll(genericQueryService.getAll(t));
    }

    @Update
    public abstract void update(T obj);

    @Update
    public abstract void update(List<T> obj);

    @Delete
    public abstract void delete(T obj);

    @Delete
    public abstract void delete(List<T> obj);

    @RawQuery
    protected abstract Long delete(SupportSQLiteQuery query);

    public Long delete(Class<T> t, long id) {
        GenericQueryService<T> genericQueryService = new GenericQueryService<>();

        this.delete(genericQueryService.deleteById(t, id));

        return id;
    }

    public String delete(Class<T> t, String name) {
        GenericQueryService<T> genericQueryService = new GenericQueryService<>();

        this.delete(genericQueryService.deleteById(t, name));

        return name;
    }

    // Transaction queries

//    @RawQuery
//    @Transaction
//    protected abstract R getWithTransaction(SupportSQLiteQuery query);
//
//    @RawQuery
//    @Transaction
//    protected abstract List<R> getAllWithTransaction(SupportSQLiteQuery query);
//
//
//    public R getWithTransaction(Class<T> r, long id) {
//        GenericQueryService<T, R> genericQueryService = new GenericQueryService<>();
//
//        return this.getWithTransaction(genericQueryService.getByIdWithTransaction(r, id));
//    }
//
//    public List<R> getWithTransaction(Class<T> r) {
//        GenericQueryService<T, R> genericQueryService = new GenericQueryService<>();
//
//        return this.getAllWithTransaction(genericQueryService.getAllWithTransaction(r));
//    }
}
