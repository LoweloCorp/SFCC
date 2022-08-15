package com.lowelostudents.caloriecounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lowelostudents.caloriecounter.databinding.ActivityMainBinding;
import com.lowelostudents.caloriecounter.services.AppRater;
import com.lowelostudents.caloriecounter.services.KeyboardService;
import com.lowelostudents.caloriecounter.tasks.DataPopulationTask;

import java.lang.ref.WeakReference;

// TODO create website
// TODO maybe utilize UI library / framework
// TODO Create Meal hide Added Items quantity Select
// TODO Toast service, validation service

// TODO feature calorie calculator
// TODO in app rating, donate popup
// TODO check duplicate code, seperation of concerns, write tests, make proper fragments
// TODO Errorhandling
// TODO feature planning based on Feedback
// TODO feature cross platform? convert to Kotlin

public class MainActivity extends AppCompatActivity {

    public static WeakReference<MainActivity> weakActivity;
    SearchView searchView;
    private ActivityMainBinding binding;

    // etc..
    public static MainActivity getInstance() {
        return weakActivity.get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppRater.app_launched(this);
        checkUpdates();
        checkPreferences();
        weakActivity = new WeakReference<>(MainActivity.this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.searchView = findViewById(R.id.searchView);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_graphs, R.id.navigation_dashboard, R.id.navigation_foodhub, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        searchView.clearFocus();

        KeyboardService.hideSoftKeyboard(ev, this);

        return super.dispatchTouchEvent(ev);
    }

    private void checkUpdates() {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DataPopulationTask.class).build();
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        workManager.enqueue(oneTimeWorkRequest);
    }


    private void checkPreferences() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        // Check if we need to display our OnboardingSupportFragment
        if (!sharedPreferences.getBoolean("isOnBoard?", false)) {
            // The user hasn't seen the OnboardingSupportFragment yet, so show it
            startActivity(new Intent(this, OnboardingActivity.class));
        }
    }

}