package com.example.coffeediaries.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeediaries.CoffeeRecordsEntity;
import com.example.coffeediaries.MainActivity;
import com.example.coffeediaries.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private BrewAdapter brewAdapter;
    private LinearLayout emptyStateLayout;
    private List<CoffeeRecordsEntity> brewList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initializes views
        recyclerView = binding.recyclerViewBrews;
        emptyStateLayout = binding.emptyStateLayout;

        // Initializes brew list and adapter
        brewList = new ArrayList<>();
        brewAdapter = new BrewAdapter(brewList);

        // Sets up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(brewAdapter);

        // Loads data from database
        loadBrewData();

        return root;
    }
    
    private void loadBrewData() {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            try {
                // Gets all brews from the database
                List<CoffeeRecordsEntity> allBrews = mainActivity.getAllBrews();
                
                if (allBrews != null && !allBrews.isEmpty()) {
                    // Shows RecyclerView and hides empty state
                    brewList.clear();
                    brewList.addAll(allBrews);
                    
                    // Shows newest entries first
                    java.util.Collections.reverse(brewList);
                    
                    brewAdapter.updateBrewList(brewList);
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyStateLayout.setVisibility(View.GONE);
                } else {
                    // Shows empty state and hide RecyclerView
                    recyclerView.setVisibility(View.GONE);
                    emptyStateLayout.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                // Handles any database errors
                recyclerView.setVisibility(View.GONE);
                emptyStateLayout.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Refreshes data when fragment becomes visible
        loadBrewData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}