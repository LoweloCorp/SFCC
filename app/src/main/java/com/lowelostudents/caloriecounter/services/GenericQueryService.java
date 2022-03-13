package com.lowelostudents.caloriecounter.services;

import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.Locale;

public final class GenericQueryService<T> {

    public SimpleSQLiteQuery getAll(Class<T> t) {
        return new SimpleSQLiteQuery("SELECT * FROM " + t.getSimpleName());
    }

    public SimpleSQLiteQuery getById(Class<T> t, long id) {
        String tableName = t.getSimpleName();
        String where = tableName.toLowerCase(Locale.ROOT);

        return new SimpleSQLiteQuery("SELECT * FROM " + tableName + " WHERE id = " + id);
    }

    public SimpleSQLiteQuery deleteById(Class<T> t, long id) {
        String tableName = t.getSimpleName();
        String where = tableName.toLowerCase(Locale.ROOT);

        return new SimpleSQLiteQuery("DELETE FROM " + tableName + " WHERE id = " + id);
    }

    public SimpleSQLiteQuery deleteById(Class<T> t, String name) {
        String tableName = t.getSimpleName();
        String where = tableName.toLowerCase(Locale.ROOT);

        return new SimpleSQLiteQuery("DELETE FROM " + tableName + " WHERE name = '" + name + "'");
    }
}
