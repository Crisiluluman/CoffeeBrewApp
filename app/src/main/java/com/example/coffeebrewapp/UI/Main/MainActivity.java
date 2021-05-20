package com.example.coffeebrewapp.UI.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeebrewapp.Data.ProfileData.ProfileData;
import com.example.coffeebrewapp.R;
import com.example.coffeebrewapp.UI.Login.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private MainViewModel viewModel;
    private TextView usernameTextview;

    private CircleImageView profileImage;


    // These static variables are required to access specific Context, Nav_header elements and the NavController itself throughout the whole application
    public static NavController navController;
    public static NavigationView navigationView;
    public static View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);

        viewModel.init();

        //Check if signed in
        checkIfSignedIn();

        // Creating toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creating drawer and setting it to close when clicking?
        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.LEFT);

        // NavigationViewer
        navigationView = findViewById(R.id.nav_view);

        //Sets username in navbar header when opening main activity
        header = navigationView.getHeaderView(0);
        usernameTextview = header.findViewById(R.id.usernameText);

        // Setting the drawer buttons to open other fragments
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_frontPage, R.id.nav_searchCoffee, R.id.nav_profile,
                R.id.nav_user_reviews)
                //    .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        CircleImageView profileImage = MainActivity.header.findViewById(R.id.nav_profile_image);

        final Observer<ProfileData> dataObserved = new Observer<ProfileData>() {
            @Override
            public void onChanged(ProfileData profileData) {
                try {
                    Picasso.with(MainActivity.header.getContext()).load(profileData.getImageSource()).into(profileImage);
                } catch (Exception e) {
                    e.printStackTrace();
                    profileImage.setImageResource(R.mipmap.ic_launcher);
                    Toast.makeText(navigationView.getContext(), "Try checking out your profile picture", Toast.LENGTH_SHORT).show();
                }
            }
        };
        viewModel.getCurrentProfileData().observe(this, dataObserved);

    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                String firebaseUsername = viewModel.getCurrentUser().getValue().getDisplayName();
                usernameTextview.setText(firebaseUsername);
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