package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.User;

import lombok.Getter;

public class UserRepo extends CRUDRepository<User> {
    @Getter
    private LiveData<User> user;

    public UserRepo(Context context) {
        super(context);
        final User.UserDao userDao = getAppdb().userDao();
        super.setCrudDao(userDao);
        // TODO get ID from backend / Data Population task
        user = userDao.getObservable(1);
    }
}
