package com.example.sumup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sumup.databinding.HomeBinding;
import com.example.sumup.databinding.SettingsBinding;

public class setting extends Fragment {
    private SettingsBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(setting.this)
                        .navigate(R.id.action_setting2_to_notifications2);
            }
        });
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(setting.this)
                        .navigate(R.id.action_setting2_to_home3);
            }

        });
        binding.savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(setting.this)
                        .navigate(R.id.action_setting2_to_myprofile2);
            }

        });
        binding.stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(setting.this)
                        .navigate(R.id.action_setting2_to_stats2);
            }

        });
    }
}
