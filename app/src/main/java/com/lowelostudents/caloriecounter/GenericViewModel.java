package com.lowelostudents.caloriecounter;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.lowelostudents.caloriecounter.data.repositories.CrudRepository;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class GenericViewModel<T> extends AndroidViewModel {
    @Getter @Setter
    private CrudRepository<T> crudRepository;

    public GenericViewModel(Application context) {
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