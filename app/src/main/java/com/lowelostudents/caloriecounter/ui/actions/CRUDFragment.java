package com.lowelostudents.caloriecounter.ui.actions;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.FragmentPersistenceBinding;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.CRUDViewModel;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.SneakyThrows;

public abstract class CRUDFragment<T> extends AppCompatActivity {

    @Getter
    private FragmentPersistenceBinding binding;
    @Getter
    private CRUDViewModel<T> model;

    protected abstract void save(T t);

    @SneakyThrows
    protected void setEventHandlers(CRUDViewModel<T> controller) {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();
        Method finish = model.getClass().getMethod("finish", Activity.class);

        eventHandlingService.onClickInvokeMethod(binding.cancelButton, model, finish, this);
    }

    protected void init(CRUDViewModel<T> model) {
        binding = FragmentPersistenceBinding.inflate(getLayoutInflater());
        this.model = model;

        setContentView(binding.getRoot());
        setEventHandlers(model);
    }
}