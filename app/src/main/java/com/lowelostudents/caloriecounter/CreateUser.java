package com.lowelostudents.caloriecounter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.FragmentCreateUserBinding;
import com.lowelostudents.caloriecounter.models.entities.User;
import com.lowelostudents.caloriecounter.ui.viewmodels.UserViewModel;

import java.util.UUID;

public class CreateUser extends Fragment {
    FragmentCreateUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        this.binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    User user = new User(UUID.randomUUID().toString(), binding.username.getText().toString(), Double.parseDouble(binding.userCalories.getText().toString()));
                    userViewModel.insert(user);

                    requireActivity().finish();
                }
            }
        });
        // Inflate the layout for this fragment

        View root = binding.getRoot();

        return root;
    }

    private boolean validate() {
        boolean validated = true;

        if (this.binding.username.getText().toString().isEmpty()) {
            this.binding.username.setError("Please enter with characters A-Z a-z");
            validated = false;
        }

        // TODO validate service

        if (!this.binding.userCalories.getText().toString().matches("^(0|([1-9][0-9]*))(\\.[0-9]+)?$")) {
            this.binding.userCalories.setError("Please enter whole number");
            validated = false;
        }

        return validated;
    }
}