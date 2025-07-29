package com.example.coffeediaries.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.coffeediaries.AppDatabase;
import com.example.coffeediaries.MainActivity;
import com.example.coffeediaries.R;
import com.example.coffeediaries.databinding.FragmentSlideshowBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private AppDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        // Set the text from string resources
        slideshowViewModel.setText(getString(R.string.frequency_metrics));
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        
        // Initializes the database
        db = AppDatabase.getInstance(getContext());
        
        // Loads metrics
        loadMetrics();
        
        return root;
    }

    private void loadMetrics() {
        Executors.newSingleThreadExecutor().execute(() -> {
            int todayCount = getTodayCount();
            int weekCount = getWeekCount();
            int monthCount = getMonthCount();

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    binding.textTodayCups.setText(todayCount + " cup(s) of coffee consumed");
                    binding.textWeekCups.setText(weekCount + " cup(s) of coffee consumed");
                    binding.textMonthCups.setText(monthCount + " cup(s) of coffee consumed");
                });
            }
        });
    }

    private int getTodayCount() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String today = dateFormat.format(new Date());
        return db.coffeeRecordDao().getCountByDate(today);
    }

    private int getMonthCount() {
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDay = calendar.getTime();
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDay = calendar.getTime();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String firstDayStr = dateFormat.format(firstDay);
        String lastDayStr = dateFormat.format(lastDay);
        
        return db.coffeeRecordDao().getCountBetweenDates(firstDayStr, lastDayStr);
    }

    private int getWeekCount() {
        Calendar calendar = Calendar.getInstance();
        
        // Sets the start of the week using .getFirstDayOfWeek()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date firstDay = calendar.getTime();
        
        // Sets the end of the week
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date lastDay = calendar.getTime();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String firstDayStr = dateFormat.format(firstDay);
        String lastDayStr = dateFormat.format(lastDay);
        
        return db.coffeeRecordDao().getCountBetweenDates(firstDayStr, lastDayStr);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (db != null) {
            loadMetrics();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}