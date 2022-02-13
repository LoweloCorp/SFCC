package com.lowelostudents.caloriecounter.ui.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.lowelostudents.caloriecounter.databinding.FragmentStatsBinding;
import com.lowelostudents.caloriecounter.ui.viewmodels.StatsViewModel;

public class StatsFragment extends Fragment {

    private StatsViewModel statsViewModel;
    private FragmentStatsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.statsViewModel =
                new ViewModelProvider(this).get(StatsViewModel.class);
        this.binding = FragmentStatsBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}