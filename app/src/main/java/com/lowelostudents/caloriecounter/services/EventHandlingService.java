package com.lowelostudents.caloriecounter.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.lowelostudents.caloriecounter.GenericController;
import com.lowelostudents.caloriecounter.ui.actions.CreateFood;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventHandlingService {
    private static EventHandlingService instance;

    public static synchronized EventHandlingService getInstance() {
        if (instance == null) instance = new EventHandlingService();

        return instance;
    }

/*    public void onClickStartActivityFromContext(View view, Fragment fragment, Class<?> activity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment.getActivity(), CreateFood.class);
                fragment.startActivity(intent);
            }
        });
    }*/

    public void onClickStartActivityFromContext(View view, Context context, Class<?> activity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreateFood.class);
                context.startActivity(intent);
            }
        });
    }

    public <T extends GenericController> void onClickInvokeMethod(View view, T controller, Method method){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    method.invoke(controller);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T extends GenericController> void onClickInvokeMethod(View view, T controller, Method method, Object... parameters){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    method.invoke(controller, parameters);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
