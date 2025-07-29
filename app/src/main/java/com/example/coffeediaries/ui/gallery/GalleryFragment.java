package com.example.coffeediaries.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeediaries.CoffeeRecordsEntity;
import com.example.coffeediaries.MainActivity;
import com.example.coffeediaries.databinding.FragmentGalleryBinding;
import com.example.coffeediaries.ui.home.BrewAdapter;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private RecyclerView recyclerView;
    private BrewAdapter brewAdapter;
    private LinearLayout emptyStateLayout;
    private List<CoffeeRecordsEntity> brewList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initializes views
        recyclerView = binding.recyclerViewBrewsRating;
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
                List<CoffeeRecordsEntity> allBrews = mainActivity.getAllRatingOrder();

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