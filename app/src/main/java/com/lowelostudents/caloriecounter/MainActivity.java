package com.lowelostudents.caloriecounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.lowelostudents.caloriecounter.tasks.DataPopulationTask;

import java.lang.ref.WeakReference;

// TODO fix create meal index out of bounds
// TODO DONATE FRAGMENT, Finalize Onboarding Design put back in Preferences
// TODO PERSISTENT DATABASE, Every Day new day, PAY FEE, SETUP CI/CD / UPdate process or something like that, MAKE SCREENSHOTS AND DESCRIPTION, PUBLISH
// TODO Create Meal hide Added Items quantity Select
// TODO Toast service, validation service

// TODO Action bar and navigate to Foodhub when typing in search if not already
// TODO check duplicate code, seperation of concerns, check overuse eventhandling service, write tests
// TODO Errorhandling

public class MainActivity extends AppCompatActivity {

    public static WeakReference<MainActivity> weakActivity;
    // etc..
    public static MainActivity getInstance() {
        return weakActivity.get();
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpdates();
//        checkPreferences();
        startActivity(new Intent(this, OnboardingActivity.class));
        weakActivity = new WeakReference<>(MainActivity.this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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