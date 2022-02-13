package com.lowelostudents.caloriecounter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lowelostudents.caloriecounter.databinding.FragmentHeaderBinding;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.models.CreateFood;
import com.lowelostudents.caloriecounter.ui.models.CreateMeal;

public class HeaderFragment extends Fragment {

    private FragmentHeaderBinding binding;

    private void setEventHandlers() {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();

        eventHandlingService.onClickStartActivityFromContext(binding.createFood, this.getContext(), CreateFood.class);
        eventHandlingService.onClickStartActivityFromContext(binding.createMeal, this.getContext(), CreateMeal.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.binding = FragmentHeaderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setEventHandlers();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }

}