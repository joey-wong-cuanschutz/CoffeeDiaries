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
import com.example.coffeediaries.R;
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
        // Set the text from string resources
        settingsViewModel.setText(getString(R.string.data_settings));
        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Pops up with a dialog to confirm the user wants to clear all their data
        // Calls the clearAllData() method if the user confirms.
        binding.btnClearData.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.clear_all_data_title))
                    .setMessage(getString(R.string.clear_all_data_message))
                    .setPositiveButton(getString(R.string.clear_data_button), (dialog, which) -> {
                        clearAllData();
                    })
                    .setNegativeButton(getString(R.string.cancel_button), null)
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
                Toast.makeText(getContext(), getString(R.string.data_cleared_success), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), getString(R.string.data_cleared_error), Toast.LENGTH_SHORT).show();
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