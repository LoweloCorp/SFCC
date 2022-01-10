package com.lowelostudents.caloriecounter.ui.foodhub;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class FoodhubViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FoodhubViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<String> arrList(String... string) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < string.length; i++) {
            arrayList.add(string[i]);
        }

        return arrayList;
    }
}