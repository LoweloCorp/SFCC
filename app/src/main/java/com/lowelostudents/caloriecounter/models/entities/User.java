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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @PrimaryKey
    @EqualsAndHashCode.Include
    @NonNull
    protected UUID id;
    private String token;
    private String name;
    protected double calTotal = 0;

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
