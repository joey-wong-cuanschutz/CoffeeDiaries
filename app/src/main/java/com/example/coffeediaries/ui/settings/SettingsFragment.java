package com.example.coffeediaries.ui.settings;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeediaries.MainActivity;
import com.example.coffeediaries.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Pops up with a dialog to confirm the user wants to clear all their data
        // Calls the clearAllData() method if the user confirms.
        binding.btnClearData.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Clear All Data")
                    .setMessage("Are you sure you want to delete all of your data? This cannot be undone...")
                    .setPositiveButton("Clear Data", (dialog, which) -> {
                        clearAllData();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        return root;
    }

    // Clears all the database entities with error handling
    private void clearAllData() {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            try {
                // Clear all data from the database
                mainActivity.clearAllData();
                Toast.makeText(getContext(), "All data cleared", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error clearing data", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}