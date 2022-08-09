package com.lowelostudents.caloriecounter.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.lowelostudents.caloriecounter.enums.ActivityMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

// TODO check overusage of service, rework of this service build generic eventlistneers instead
public class EventHandlingService {
    private static EventHandlingService instance;

    public static synchronized EventHandlingService getInstance() {
        if (instance == null) instance = new EventHandlingService();

        return instance;
    }

    public void onClickStartActivityFromContext(View view, Context context, Class<?> activity) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity);
                context.startActivity(intent);
            }
        });
    }

    public void onClickLaunchActivityFromContext(View view, Context context, Class<?> activity, ActivityResultLauncher<Intent> activityResultLauncher) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity);
                activityResultLauncher.launch(intent);
            }
        });
    }

    public final void onClickStartActivityInMode(View view, Context context, Class<?> activity, ActivityMode parameters) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity);
                intent.putExtra("mode", parameters);
                context.startActivity(intent);
            }
        });
    }


    public <T> void onClickInvokeMethod(View view, T controller, Method method) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Context context = view.getContext().getApplicationContext();
                    CharSequence methodName = method.getName();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, methodName, duration);
                    toast.show();
                    method.invoke(controller);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T> void onClickInvokeMethod(View view, T controller, Method method, Object... parameters) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Context context = view.getContext().getApplicationContext();
                    CharSequence methodName = method.getName();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, methodName, duration);
                    toast.show();
                    method.invoke(controller, parameters);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T> void onChangedInvokeMethod(LifecycleOwner lifecycleOwner, LiveData<?> dataSet, T controller, Method method) {
        dataSet.observe(lifecycleOwner, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                try {
                    method.invoke(controller, o);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public <T> Disposable onChangedInvokeMethod(Observable<?> dataSet, T controller, Method method) {
        return dataSet.observeOn(AndroidSchedulers.mainThread()).subscribe( value -> {
            method.invoke(controller, value);
        });
    }



}
