package com.lowelostudents.caloriecounter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lowelostudents.caloriecounter.databinding.ActivityMainBinding;
import com.lowelostudents.caloriecounter.tasks.DataPopulationTask;

import java.lang.ref.WeakReference;


// TODO Activity scanner
// TODO Toast service
// TODO settings button tab
// TODO remove from added items meal meal added items color button, Meal SERVING PACK CUSTOM Hide
// TODO Themeing
// TODO ONBOARDING AND DONATE BUTTON
// TODO PERSISTENT DATABASE, Every Day new day, PAY FEE, SETUP CI/CD / UPdate process or something like that, MAKE SCREENSHOTS AND DESCRIPTION, PUBLISH
// TODO Action bar and navigate to Foodhub when typing in search if not already
// TODO check duplicate code, seperation of concerns, check overuse eventhandling service, write tests
// TODO Errorhandling
// TODO NONBLOCKING way for user

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
        weakActivity = new WeakReference<>(MainActivity.this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_graphs, R.id.navigation_dashboard, R.id.navigation_foodhub)
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


}