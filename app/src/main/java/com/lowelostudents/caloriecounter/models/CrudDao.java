package com.lowelostudents.caloriecounter.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.util.List;


@Dao
public interface CrudDao<T> {
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
    List<T> getAll(SupportSQLiteQuery query);

    @RawQuery
    T get(SupportSQLiteQuery query);

    @RawQuery
    @Transaction
    List<T> getAllWithTransaction(SupportSQLiteQuery query);

    @RawQuery
    @Transaction
    T getWithTransaction(SupportSQLiteQuery query);
}
