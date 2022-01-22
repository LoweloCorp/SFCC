package com.lowelostudents.caloriecounter;

import android.app.Activity;

import java.lang.reflect.Parameter;

public abstract class GenericController {
    public abstract <T extends Activity> void finish(T t);
}
