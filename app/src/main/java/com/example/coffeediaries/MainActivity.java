package com.example.coffeediaries;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

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
            if (destination.getId() == R.id.nav_brew_input) {
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // define a method to load the data from the db
    public void loadAllData() {
        brewList.clear();
        List<CoffeeRecordsEntity> newBrewList = db.coffeeRecordDao().getAll();
        Log.i("input-result", newBrewList.toString());
        brewList.addAll(newBrewList);
    }

    // add a new coffee brew record to the db
    public void addBrew(CoffeeRecordsEntity coffeeRecordsEntity) {
        long id = db.coffeeRecordDao().insertRecord(coffeeRecordsEntity);
    }
}