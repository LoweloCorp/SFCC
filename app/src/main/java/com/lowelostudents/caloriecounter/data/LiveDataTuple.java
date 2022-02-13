package com.lowelostudents.caloriecounter.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LiveDataTuple<T, R> extends MediatorLiveData<List<?>> {
    private List<T> listOne = Collections.emptyList();
    private List<R> listTwo = Collections.emptyList();

    public LiveDataTuple(LiveData<List<T>> liveListOne, LiveData<List<R>> liveListTwo) {
        addSource(liveListOne, listOne -> {
            if (listOne != null) {
                this.listOne = listOne;
            }
            setValue(combinedList(listOne, this.listTwo));
        });

        addSource(liveListTwo, listTwo -> {
            if (listTwo != null) {
                this.listTwo = listTwo;
            }

            setValue(combinedList(this.listOne, listTwo));
        });
    }

    private List<Object> combinedList(List<T> listOne, List<R> listTwo) {
        List<Object> combinedList = new ArrayList<>();
        combinedList.addAll(listOne);
        combinedList.addAll(listTwo);

        return combinedList;
    }
}