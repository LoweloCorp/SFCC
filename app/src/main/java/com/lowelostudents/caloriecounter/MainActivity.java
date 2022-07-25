package com.lowelostudents.caloriecounter;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lowelostudents.caloriecounter.data.repositories.UserRepo;
import com.lowelostudents.caloriecounter.databinding.ActivityMainBinding;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.tasks.DataPopulationTask;
import com.lowelostudents.caloriecounter.ui.viewmodels.UserViewModel;

// TODO After create Food/Meal navigate to FoodHub
// TODO Navigation when search input check if FoodHub activity if not navigate to it
// TODO Refactoring, Delete only by ID, Remove from day animation / Feedback, Enter Calories, Impement Action bar
// TODO check overuse eventhandling service, seperation of concerns
// TODO find nonblockin way for calculation of pie entries with users calory pensum
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpdates();

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