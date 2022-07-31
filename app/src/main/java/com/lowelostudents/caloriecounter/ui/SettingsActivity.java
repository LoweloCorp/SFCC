package com.lowelostudents.caloriecounter.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.ui.viewmodels.UserViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

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

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private User user;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            EditTextPreference name = findPreference("name");
            EditTextPreference calories = findPreference("calories");

            this.user = userViewModel.getUser().blockingFirst();

            name.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    User updatedUser = new User(user.getToken(), newValue.toString(), user.getCalTotal());
                    updatedUser.setId(user.getId());
                    userViewModel.update(updatedUser);
                    name.setText(newValue.toString());
                    return false;
                }
            });

            calories.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    User updatedUser = new User(user.getToken(), user.getName(), Integer.parseInt(newValue.toString()));
                    updatedUser.setId(user.getId());
                    userViewModel.update(updatedUser);
                    calories.setText(newValue.toString());
                    return false;
                }
            });
        }
    }
}