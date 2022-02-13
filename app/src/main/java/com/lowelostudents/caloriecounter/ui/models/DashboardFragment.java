package com.lowelostudents.caloriecounter.ui.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lowelostudents.caloriecounter.databinding.FragmentDashboardBinding;
import com.lowelostudents.caloriecounter.ui.GenericRecyclerViewAdapter;
import com.lowelostudents.caloriecounter.ui.viewmodels.DashboardViewModel;

public class DashboardFragment extends Fragment {

    // TODO Observable and dummy functionality

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
//    private ArrayList<Object> items = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        this.binding = FragmentDashboardBinding.inflate(inflater, container, false);

        final RecyclerView foodList = binding.foodList;
        final GenericRecyclerViewAdapter recyclerViewAdapter = new GenericRecyclerViewAdapter(this.getContext());
        foodList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        foodList.setAdapter(recyclerViewAdapter);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}