package com.lowelostudents.caloriecounter.ui.header;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.databinding.FragmentHeaderBinding;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.actions.CreateFood;
import com.lowelostudents.caloriecounter.ui.actions.CreateMeal;

import org.json.JSONObject;

public class HeaderFragment extends Fragment {

    private HeaderViewModel headerViewModel;
    private FragmentHeaderBinding binding;

    private void setEventHandlers() {
        EventHandlingService eventHandlingService = EventHandlingService.getInstance();

        eventHandlingService.onClickStartActivityFromContext(binding.createFood, this.getContext(), CreateFood.class);
        eventHandlingService.onClickStartActivityFromContext(binding.createMeal, this.getContext(), CreateMeal.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        headerViewModel = new ViewModelProvider(this).get(HeaderViewModel.class);

        binding = FragmentHeaderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setEventHandlers();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}