package com.example.coffeebrewapp.UI.Main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.UI.Login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private MainViewModel viewModel;
    private TextView usernameTextview;

    // Available floating buttons, so other fragments can hide them on their page
    public static FloatingActionButton fab_search;

    // Navigation controller should also be available to all classes
    public static NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);

        //Check if signed in
        checkIfSignedIn();
        
        
        // Creating toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Creating drawer and setting it to close when clicking?
        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.LEFT);

        // NavigationViewer
        NavigationView navigationView = findViewById(R.id.nav_view);


        //Sets username in navbar header when opening main activity
        View header = navigationView.getHeaderView(0);
        usernameTextview = header.findViewById(R.id.usernameText);

        // Setting the drawer buttons to open other fragments
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_frontPage, R.id.nav_searchCoffee, R.id.nav_profile,
                R.id.nav_edit_reviews, R.id.nav_followed_people)
                //    .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // Floating button to search page
        fab_search = findViewById(R.id.floating_search_button);
        fab_search.show();
        fab_search.setOnClickListener(v -> {
            navController.navigate(R.id.nav_searchCoffee);
        });

    }
    
    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                String firebaseUsername = viewModel.getCurrentUser().getValue().getDisplayName();
                usernameTextview.setText(firebaseUsername);
                Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
            } else
                startLoginActivity();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
        viewModel.signOut();
    }

}