package com.lowelostudents.caloriecounter.models.entities;

import androidx.sqlite.db.SimpleSQLiteQuery;

public final class GenericQueries {

    public final SimpleSQLiteQuery GET;

    public GenericQueries(Object object) {
        this.GET = new SimpleSQLiteQuery("SELECT * FROM " + getTableName(object));
    }

    public GenericQueries(Object object, String where, String equals) {
        this.GET = new SimpleSQLiteQuery("SELECT * FROM " + getTableName(object) + "WHERE " + where + " = :" + equals);
    }

    private String getTableName(Object object) {
        return object.getClass().getSimpleName();
    }
}
