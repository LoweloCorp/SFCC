package com.lowelostudents.caloriecounter.ui;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.lowelostudents.caloriecounter.databinding.FragmentPersistenceBinding;
import com.lowelostudents.caloriecounter.services.EventHandlingService;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.SneakyThrows;

public abstract class CRUDActivity<T> extends AppCompatActivity {

    @Getter
    private FragmentPersistenceBinding binding;
    @Getter
    private CRUDViewModel<T> model;

    protected abstract void save(T t, EventHandlingService eventHandlingService);

    @SneakyThrows
    protected void setEventHandlers() {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method finish = model.getClass().getMethod("finish", Activity.class);

        eventHandlingService.onClickInvokeMethod(binding.cancelButton, model, finish, this);
    }

    protected void init(CRUDViewModel<T> model) {
        this.binding = FragmentPersistenceBinding.inflate(getLayoutInflater());
        this.model = model;

        setContentView(binding.getRoot());
        setEventHandlers();
    }
}