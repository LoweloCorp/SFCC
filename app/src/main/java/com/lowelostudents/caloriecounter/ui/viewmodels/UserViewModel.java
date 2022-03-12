package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lowelostudents.caloriecounter.data.repositories.UserRepo;
import com.lowelostudents.caloriecounter.models.entities.User;

import lombok.Getter;

public class UserViewModel extends AndroidViewModel {
    @Getter
    private final UserRepo repo;
    @Getter
    private final LiveData<User> user;

    public UserViewModel(Application context) {
        super(context);
        repo = new UserRepo(context.getApplicationContext());
        user = repo.getUser();
    }

    public Long insert(User t) {
        return repo.insert(t);
    }
}
