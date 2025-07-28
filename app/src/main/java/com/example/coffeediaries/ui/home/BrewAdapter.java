package com.example.coffeediaries.ui.home;

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
            tvCoffeeName.setText(coffeeName.equals("Unknown") ? "Unnamed Coffee" : coffeeName);

            // Sets date
            String brewDate = brew.getBrewDate();
            tvBrewDate.setText(brewDate.equals("Unknown") ? "N/A" : brewDate);

            // Sets rating
            double rating = brew.getRating();
            if (rating > 0) {
                tvRating.setText("Rating: " + rating + "/5");
            } else {
                tvRating.setText("Not rated");
            }

            // Sets brew method
            String brewMethod = brew.getBrewMethod();
            tvBrewMethod.setText(brewMethod.equals("Unknown") ? "N/A" : brewMethod);

            // Sets brew time
            String brewTime = brew.getBrewTime();
            tvBrewTime.setText(brewTime.equals("Unknown") ? "N/A" : brewTime);

            // Sets coffee amount
            double gramsCoffee = brew.getGramsCoffee();
            if (gramsCoffee > 0) {
                if (gramsCoffee == (int)gramsCoffee) {
                    tvGramsCoffee.setText(String.valueOf((int)gramsCoffee));
                } else {
                    tvGramsCoffee.setText(String.valueOf(gramsCoffee));
                }
            } else {
                tvGramsCoffee.setText("N/A");
            }

            // Sets calories
            double calories = brew.getCalories();
            if (calories > 0) {
                tvCalories.setText(String.valueOf((int)calories));
            } else {
                tvCalories.setText("N/A");
            }

            // Sets comment and hides if there is no comment
            String comment = brew.getComment();
            if (comment != null && !comment.equals("Unknown") && !comment.trim().isEmpty()) {
                tvComment.setText(comment);
                tvComment.setVisibility(View.VISIBLE);
            } else {
                tvComment.setVisibility(View.GONE);
            }
        }
    }
}
