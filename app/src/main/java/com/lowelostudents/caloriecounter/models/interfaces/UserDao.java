package com.lowelostudents.caloriecounter.models.interfaces;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.lowelostudents.caloriecounter.models.CRUDDao;
import com.lowelostudents.caloriecounter.models.entities.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class UserDao extends CRUDDao<User> {
    @Query("SELECT * FROM User")
    public abstract LiveData<List<User>> getAllObservable();

    @Query("SELECT * FROM User WHERE id = :id")
    public abstract LiveData<User> getObservable(long id);

    @Query("SELECT * FROM User WHERE id = :id")
    public abstract Observable<User> get(long id);
}
