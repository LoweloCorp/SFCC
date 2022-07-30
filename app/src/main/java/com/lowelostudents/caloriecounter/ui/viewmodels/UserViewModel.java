package com.lowelostudents.caloriecounter.ui.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.lowelostudents.caloriecounter.data.repositories.UserRepo;
import com.lowelostudents.caloriecounter.models.entities.User;

import io.reactivex.rxjava3.core.Observable;
import lombok.Getter;

public class UserViewModel extends AndroidViewModel {
    @Getter
    private final UserRepo repo;
    @Getter
    private final Observable<User> user;

    public UserViewModel(Application context) {
        super(context);
        repo = new UserRepo(context.getApplicationContext());
        user = repo.getUser();
    }

    public void insert(User t) {
        repo.insert(t);
    }

    public void update(User t) {
        repo.update(t);
    }
}
