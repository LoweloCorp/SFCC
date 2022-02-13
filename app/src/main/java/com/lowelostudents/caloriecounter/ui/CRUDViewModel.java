package com.lowelostudents.caloriecounter.ui;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.lowelostudents.caloriecounter.data.CRUDRepository;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


public abstract class CRUDViewModel<T> extends AndroidViewModel {
    @Getter @Setter
    private CRUDRepository<T> crudRepository;

    public CRUDViewModel(Application context) {
        super(context);
    }

    public void insert(T t){
        crudRepository.insert(t);
    }

    public void insertAll(List<T> t) {
        crudRepository.insertAll(t);
    }

    public void update(T t) {
        crudRepository.update(t);
    }

    public void updateAll(List<T> t) {
        crudRepository.updateAll(t);
    }

    public void delete(T t) {
        crudRepository.delete(t);
    }

    public void deleteAll(T t) {
        crudRepository.delete(t);
    }

    public <A extends Activity> void finish(A activity) {
        activity.finish();
    }
}