package com.lowelostudents.caloriecounter.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class EventHandlingService {
    private static EventHandlingService instance;

    public static synchronized EventHandlingService getInstance() {
        if (instance == null) instance = new EventHandlingService();

        return instance;
    }

    public void onClickStartActivityFromContext(View view, Context context, Class<?> activity){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity);
                context.startActivity(intent);
            }
        });
    }

    public <T> void onClickInvokeMethod(View view, T controller, Method method){
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

    public <T> void onClickInvokeMethod(View view, T controller, Method method, Object... parameters){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.i("Parameters", Arrays.toString(parameters));
                    method.invoke(controller, parameters);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T> void onChangedInvokeMethod(LifecycleOwner lifecycleOwner, MediatorLiveData<?> dataSet, T controller, Method method) {
        dataSet.observe(lifecycleOwner, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                Log.i("MEINDATASET", dataSet.getValue().toString());
                try {
                    method.invoke(controller, o);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
