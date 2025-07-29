package com.example.coffeediaries;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.coffeediaries.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private AppDatabase db;
    // all coffee brews will be stored in a list from the db query
    private List<CoffeeRecordsEntity> brewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigates to the BrewInputForm fragment
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_brew_input);

            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Adds the destination change listener to hide or show the FAB
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_brew_input || destination.getId() == R.id.nav_settings) {
                binding.appBarMain.fab.hide();
            } else {
                binding.appBarMain.fab.show();
            }
        });

        // create db - if the db does not exist it will be created - code located in AppDatabase.class file
        db = AppDatabase.getInstance(getApplicationContext());

        // Had to initialize the brewList or it will crash the app thanks to the loadAllData function
        brewList = new ArrayList<>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            // Navigate to settings fragment
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // define a method to load the data from the db
    public void loadAllData() {
        try {
            brewList.clear();
        } catch (RuntimeException e) {
            Toast.makeText(this, "Unable to clear data", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }

        Log.i("loadAllData", "cleared list");
        List<CoffeeRecordsEntity> newBrewList = db.coffeeRecordDao().getAll();
        Log.i("loadAllData", "queried db");
        Log.i("loadAllData", newBrewList.toString());
        brewList.addAll(newBrewList);
    }

    // add a new coffee brew record to the db
    public void addBrew(CoffeeRecordsEntity coffeeRecordsEntity) {
        long id = db.coffeeRecordDao().insertRecord(coffeeRecordsEntity);
        Log.i("insertion-id", String.valueOf(id));
        loadAllData();
    }

    // Grabs all brews from the database for the RecyclerView
    public List<CoffeeRecordsEntity> getAllBrews() {
        if (db != null) {
            return db.coffeeRecordDao().getAll();
        }
        return new ArrayList<>();
    }

    public List<CoffeeRecordsEntity> getAllRatingOrder() {
        if (db != null) {
            return db.coffeeRecordDao().getAllRatingOrder();
        }
        return new ArrayList<>();
    }

    // Clears all data from the database
    public void clearAllData() {
        if (db != null) {
            db.coffeeRecordDao().deleteAll();
            brewList.clear();
            Log.i("clearAllData", "All data cleared from database");
        }
    }
}