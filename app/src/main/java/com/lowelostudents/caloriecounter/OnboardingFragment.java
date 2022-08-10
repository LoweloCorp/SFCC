package com.lowelostudents.caloriecounter;

import static androidx.core.app.FrameMetricsAggregator.ANIMATION_DURATION;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lowelostudents.caloriecounter.databinding.FragmentDashboardBinding;
import com.lowelostudents.caloriecounter.databinding.FragmentOnboardingBinding;
import com.lowelostudents.caloriecounter.ui.viewmodels.DashboardViewModel;
import com.lowelostudents.caloriecounter.ui.viewmodels.UserViewModel;

import java.util.Objects;

public class OnboardingFragment extends Fragment {

    private FragmentOnboardingBinding binding;
    private final int currPos;

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