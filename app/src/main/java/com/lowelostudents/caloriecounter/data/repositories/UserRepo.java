package com.lowelostudents.caloriecounter.data.repositories;

import android.content.Context;

import com.lowelostudents.caloriecounter.data.CRUDRepository;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.models.interfaces.UserDao;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class UserRepo extends CRUDRepository<User> {
    @Getter
    private static Observable<User> user;

    private static UserDao userDao;

    public UserRepo(Context context) {
        super(context);
        userDao = getAppdb().userDao();
        super.setCrudDao(userDao);
        user = userDao.getFirstUser();
    }
}
