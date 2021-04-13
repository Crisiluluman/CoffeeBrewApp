package com.example.coffeebrewapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.coffeebrewapp.UI.Login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavController navController;
    private Toolbar toolbar;

    // Available floating buttons, so other fragments can hide them on their page
    public static FloatingActionButton fab_search;
    public static FloatingActionButton fab_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Actionbar stuff
       /*
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        */


        // Creating drawer and setting it to close when clicking?
        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.LEFT);

        // NavigationViewer
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Attempt to set the drawer buttons to open other fragments

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_frontPage, R.id.nav_searchCoffee) // TODO: Add more pages
                //         .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // Floating button to search page
        fab_search = findViewById(R.id.floating_search_button);
        fab_search.hide();
        fab_search.setOnClickListener(v -> {
            navController.navigate(R.id.nav_searchCoffee);
        });

        // Floating button to add review page
        fab_add = findViewById(R.id.floating_add_button);
        fab_add.show();
        fab_add.setOnClickListener(v -> {
            navController.navigate(R.id.nav_add_coffee_review);
        });

        Toast.makeText(this, "HELLO", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu_bar, menu);
        return true;
    }

    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void openDrawer(MenuItem item) {
        drawer.openDrawer(GravityCompat.START);
    }

    public void navMenuLogout(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}