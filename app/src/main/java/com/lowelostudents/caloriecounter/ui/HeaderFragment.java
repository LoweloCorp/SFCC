package com.lowelostudents.caloriecounter.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lowelostudents.caloriecounter.R;
import com.lowelostudents.caloriecounter.databinding.FragmentHeaderBinding;

public class HeaderFragment extends Fragment {

    private HeaderViewModel headerViewModel;
    private FragmentHeaderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        headerViewModel =
                new ViewModelProvider(this).get(HeaderViewModel.class);

        binding = FragmentHeaderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SearchView searchView = binding.searchView;
        // TODO Search

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}