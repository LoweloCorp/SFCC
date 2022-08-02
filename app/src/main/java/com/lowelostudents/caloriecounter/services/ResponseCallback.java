package com.lowelostudents.caloriecounter.services;

import com.lowelostudents.caloriecounter.models.entities.Food;

import org.json.JSONException;

public interface ResponseCallback {
        void onResult(Food food);
        void onError(Exception e);
}
