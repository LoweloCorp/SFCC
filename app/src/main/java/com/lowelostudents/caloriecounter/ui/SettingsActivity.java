package com.lowelostudents.caloriecounter.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.lowelostudents.caloriecounter.MainActivity;
import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.ui.viewmodels.UserViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

// TODO BRÖKEN
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavController navController = Navigation.findNavController(MainActivity.getInstance(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_dashboard);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private Observable<User> user;

        Disposable disposable;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            EditTextPreference name = findPreference("name");
            EditTextPreference calories = findPreference("calories");

            this.user = userViewModel.getUser();

            this.disposable = this.user.observeOn(AndroidSchedulers.mainThread()).subscribe( user -> {
                name.setText(user.getName());
                calories.setText(String.valueOf(user.getCalTotal()));
            });

            name.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    user.take(1).subscribe( user -> {
                        Log.w("USER", user.toString());
                        User updatedUser = new User(user.getToken(), newValue.toString(), user.getCalTotal());
                        updatedUser.setId(user.getId());
                        userViewModel.update(updatedUser);
                    });
                    return false;
                }
            });

            calories.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    user.take(1).subscribe(user -> {
                        Log.w("USER", user.toString());

                        User updatedUser = new User(user.getToken(), user.getName(), Integer.parseInt(newValue.toString()));
                        updatedUser.setId(user.getId());
                        userViewModel.update(updatedUser);
                    });
                    return false;
                }
            });
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            this.disposable.dispose();
        }
    }
}