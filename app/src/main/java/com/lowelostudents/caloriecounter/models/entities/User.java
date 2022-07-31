package com.lowelostudents.caloriecounter.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Nutrients {
    @PrimaryKey
    @EqualsAndHashCode.Include
    @NonNull
    protected UUID id;
    private String token;
    private String name;

    public User(String token, String name, double calTotal) {
        this.id = UUID.randomUUID();
        this.token = token;
        this.name = name;
        this.calTotal = calTotal;
    }

    public User() {
        this.id = UUID.randomUUID();
    }
}
