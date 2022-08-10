package com.lowelostudents.caloriecounter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OnboardingPagerAdapter extends FragmentStateAdapter {

    Fragment[] fragments;

    public OnboardingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        this.fragments = new Fragment[] {
                new Donate(),
                new OnboardingFragment(0),
                new OnboardingFragment(1),
                new OnboardingFragment(2),
                new OnboardingFragment(3),
                new OnboardingFragment(4),
                new CreateUser(),
        };
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
         return this.fragments[position];
    }

    @Override
    public int getItemCount() {
        return this.fragments.length;
    }
}
