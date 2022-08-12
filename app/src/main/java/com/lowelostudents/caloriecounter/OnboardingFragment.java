package com.lowelostudents.caloriecounter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.FragmentOnboardingBinding;
import com.lowelostudents.caloriecounter.ui.viewmodels.UserViewModel;

public class OnboardingFragment extends Fragment {

    private final int currPos;
    private FragmentOnboardingBinding binding;

    OnboardingFragment(int currPos) {
        this.currPos = currPos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentOnboardingBinding.inflate(inflater, container, false);
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Log.w("CurrPOS", String.valueOf(currPos));

        int[] pageImages = new int[]{R.drawable.today, R.drawable.create_food, R.drawable.create_meal, R.drawable.stats, R.drawable.my_settings};

        this.binding.imageView.setImageResource(pageImages[currPos]);
        View root = binding.getRoot();

        return root;
    }
}