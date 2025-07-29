package com.example.coffeediaries.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeediaries.CoffeeRecordsEntity;
import com.example.coffeediaries.R;

import java.util.List;

public class BrewAdapter extends RecyclerView.Adapter<BrewAdapter.BrewViewHolder> {

    private List<CoffeeRecordsEntity> brewList;

    public BrewAdapter(List<CoffeeRecordsEntity> brewList) {
        this.brewList = brewList;
    }

    @NonNull
    @Override
    public BrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_brew_card, parent, false);
        return new BrewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrewViewHolder holder, int position) {
        CoffeeRecordsEntity brew = brewList.get(position);
        holder.bind(brew);
    }

    @Override
    public int getItemCount() {
        return brewList != null ? brewList.size() : 0;
    }

    // Updates the brew list and refreshes the RecyclerView
    public void updateBrewList(List<CoffeeRecordsEntity> newBrewList) {
        this.brewList = newBrewList;
        notifyDataSetChanged();
    }

    static class BrewViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCoffeeName, tvBrewDate, tvBrewMethod, tvBrewTime, 
                        tvGramsCoffee, tvRating, tvCalories, tvComment;

        public BrewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCoffeeName = itemView.findViewById(R.id.tvCoffeeName);
            tvBrewDate = itemView.findViewById(R.id.tvBrewDate);
            tvBrewMethod = itemView.findViewById(R.id.tvBrewMethod);
            tvBrewTime = itemView.findViewById(R.id.tvBrewTime);
            tvGramsCoffee = itemView.findViewById(R.id.tvGramsCoffee);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            tvComment = itemView.findViewById(R.id.tvComment);
        }

        public void bind(CoffeeRecordsEntity brew) {
            // Sets coffee name
            String coffeeName = brew.getCoffeeName();
            String unknown = itemView.getContext().getString(R.string.unknown);
            String unnamedCoffee = itemView.getContext().getString(R.string.unnamed_coffee);
            String notAvailable = itemView.getContext().getString(R.string.not_available);
            String notRated = itemView.getContext().getString(R.string.not_rated);
            
            tvCoffeeName.setText(coffeeName.equals(unknown) ? unnamedCoffee : coffeeName);

            // Sets date and time
            String brewDate = brew.getBrewDate();
            String brewTimeOfDay = brew.getBrewTimeOfDay();
            if (!brewDate.equals(unknown)) {
                if (brewTimeOfDay != null && !brewTimeOfDay.equals(unknown)) {
                    // Format time for display (remove seconds if present)
                    String displayTime = formatTimeForDisplay(brewTimeOfDay);
                    tvBrewDate.setText(brewDate + " " + displayTime);
                } else {
                    tvBrewDate.setText(brewDate);
                }
            } else {
                tvBrewDate.setText(notAvailable);
            }

            // Sets rating
            double rating = brew.getRating();
            if (rating > 0) {
                tvRating.setText(itemView.getContext().getString(R.string.rating_format, rating));
            } else {
                tvRating.setText(notRated);
            }

            // Sets brew method
            String brewMethod = brew.getBrewMethod();
            tvBrewMethod.setText(brewMethod.equals(unknown) ? notAvailable : brewMethod);

            // Sets brew time
            String brewTime = brew.getBrewTime();
            tvBrewTime.setText(brewTime.equals(unknown) ? notAvailable : brewTime);

            // Sets coffee amount
            double gramsCoffee = brew.getGramsCoffee();
            if (gramsCoffee > 0) {
                if (gramsCoffee == (int)gramsCoffee) {
                    tvGramsCoffee.setText(String.valueOf((int)gramsCoffee));
                } else {
                    tvGramsCoffee.setText(String.valueOf(gramsCoffee));
                }
            } else {
                tvGramsCoffee.setText(notAvailable);
            }

            // Sets calories
            double calories = brew.getCalories();
            if (calories > 0) {
                tvCalories.setText(String.valueOf((int)calories));
            } else {
                tvCalories.setText(notAvailable);
            }

            // Sets comment and hides if there is no comment
            String comment = brew.getComment();
            if (comment != null && !comment.equals(unknown) && !comment.trim().isEmpty()) {
                tvComment.setText(comment);
                tvComment.setVisibility(View.VISIBLE);
            } else {
                tvComment.setVisibility(View.GONE);
            }
        }

        // Helper method to format time for display (removes seconds)
        private String formatTimeForDisplay(String timeWithSeconds) {
            if (timeWithSeconds != null && timeWithSeconds.length() >= 5) {
                // If time has seconds (HH:mm:ss), return only HH:mm
                if (timeWithSeconds.length() > 5 && timeWithSeconds.charAt(5) == ':') {
                    return timeWithSeconds.substring(0, 5); // Extract HH:mm
                }
            }
            return timeWithSeconds; // Return as-is if it's already HH:mm format
        }
    }
}
