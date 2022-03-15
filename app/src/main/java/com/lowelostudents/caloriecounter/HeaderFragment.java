package com.lowelostudents.caloriecounter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lowelostudents.caloriecounter.databinding.FragmentHeaderBinding;
import com.lowelostudents.caloriecounter.models.entities.Food;
import com.lowelostudents.caloriecounter.services.EventHandlingService;
import com.lowelostudents.caloriecounter.ui.models.CreateFood;
import com.lowelostudents.caloriecounter.ui.models.CreateMeal;
import com.lowelostudents.caloriecounter.ui.models.FoodhubFragment;

import java.util.List;

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
        setEventHandlers();
        View root = binding.getRoot();
        return root;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        List<Fragment> fragmentList = mainActivity.getSupportFragmentManager().getFragments();

        int id = binding.searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText editText = (EditText) binding.searchView.findViewById(id);

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                NavController navController = Navigation.findNavController(mainActivity, R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_foodhub);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }
}