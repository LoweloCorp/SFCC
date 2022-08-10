package com.lowelostudents.caloriecounter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lowelostudents.caloriecounter.databinding.FragmentDonateBinding;


public class Donate extends Fragment {
    FragmentDonateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentDonateBinding.inflate(inflater, container, false);

        this.binding.donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.paypal.com/donate/?hosted_button_id=49FYWEP529NH6"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}