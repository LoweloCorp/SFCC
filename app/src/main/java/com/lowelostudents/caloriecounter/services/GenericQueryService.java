package com.lowelostudents.caloriecounter.services;

import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.Locale;

public final class GenericQueryService<T, R> {

    public SimpleSQLiteQuery getAll(Class<T> t) {
        return new SimpleSQLiteQuery("SELECT * FROM " + t.getSimpleName());
    }

    public SimpleSQLiteQuery getById(Class<T> t, long id) {
        String tableName = t.getSimpleName();
        String where = tableName.toLowerCase(Locale.ROOT);

        return new SimpleSQLiteQuery("SELECT * FROM " + tableName + " WHERE " + where + "Id" + " = " + id);
    }

    public SimpleSQLiteQuery getAllWithTransaction(Class<T> t) {
        return new SimpleSQLiteQuery("SELECT * FROM " + t.getSimpleName());
    }

    public SimpleSQLiteQuery getByIdWithTransaction(Class<T> t, long id) {
        String tableName = t.getSimpleName();
        String where = tableName.toLowerCase(Locale.ROOT);

        return new SimpleSQLiteQuery("SELECT * FROM " + tableName + " WHERE " + where + "Id" + " = " + id);
    }

    public SimpleSQLiteQuery deleteById(Class<T> t, long id) {
        String tableName = t.getSimpleName();
        String where = tableName.toLowerCase(Locale.ROOT);

        return new SimpleSQLiteQuery("DELETE FROM " + tableName + " WHERE " + where + "Id" + " = " + id);
    }
}
