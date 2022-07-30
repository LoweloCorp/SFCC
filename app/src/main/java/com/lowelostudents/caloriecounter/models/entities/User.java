package com.lowelostudents.caloriecounter.models.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    public User(String token, String name, double calTotal) {
        this.token = token;
        this.name = name;
        this.calTotal = calTotal;
    }

    @Ignore public User() {

    }
}
