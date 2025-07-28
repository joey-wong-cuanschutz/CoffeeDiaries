package com.example.coffeediaries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeediaries.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class BrewRecyclerViewAdapter extends RecyclerView.Adapter<BrewRecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<CoffeeRecordsEntity> brewList;
    private List<CoffeeRecordsEntity> records = new ArrayList<>();

    public BrewRecyclerViewAdapter(HomeFragment context, LiveData<List<CoffeeRecordsEntity>> brewList) {
        this.context = context;
        this.brewList = brewList;
    }

    @NonNull
    @Override
    public BrewRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflates the layout (giving look to the rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.brew_recycler_view, parent, false);
        return new BrewRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrewRecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning vales to each of the views created in the brew_recycler_view layout file
        // based on the position of the recycler view
        holder.dateTextView.setText(brewList.get(position).getBrewDate());
        holder.coffeeNameTextView.setText(brewList.get(position).getCoffeeName());
        holder.brewMethodTextView.setText(brewList.get(position).getBrewMethod());
        holder.ratingTextView.setText((int) brewList.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        // get the number of items in total to display to the user
        return brewList.size();
    }

    public void setRecords(List<CoffeeRecordsEntity> newRecords) {
        this.records = newRecords;
        notifyDataSetChanged(); // simple but less efficient than ListAdapter
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grab the views from the brew_recycler_view layout file

        TextView dateTextView, coffeeNameTextView, brewMethodTextView, ratingTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.recyclerViewDate);
            coffeeNameTextView = itemView.findViewById(R.id.recyclerViewCoffeeName);
            brewMethodTextView = itemView.findViewById(R.id.recyclerViewBrewMethod);
            ratingTextView = itemView.findViewById(R.id.recyclerViewBrewRating);

        }
    }
}
