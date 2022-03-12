package com.lowelostudents.caloriecounter.models.entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.lowelostudents.caloriecounter.models.CRUDDao;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Nutrients {
    @PrimaryKey(autoGenerate = true)
    @EqualsAndHashCode.Include
    protected long id;
    private String token;
    private String name;

    public User(String token, String name, int calTotal) {
        this.token = token;
        this.name = name;
        this.calTotal = calTotal;
    }

    @Ignore public User() {

    }


    @Dao
    public abstract static class UserDao extends CRUDDao<User> {
        @Query("SELECT * FROM User")
        public abstract LiveData<List<User>> getAllObservable();

        @Query("SELECT * FROM User WHERE id = :id")
        public abstract LiveData<User> getObservable(long id);
    }
}
