package com.example.coffeebrewapp.UI.CreateCoffeeReview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.coffeebrewapp.MainActivity;
import com.example.coffeebrewapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateCoffeeActivity extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       //TEST Log.e("TAG", "\n \nonCreateView: \n\n" );

        // Hiding the other floating buttons for this fragment
        FloatingActionButton fab_add = MainActivity.fab_add;
        fab_add.setVisibility(View.GONE);

        FloatingActionButton fab_search = MainActivity.fab_search;
        fab_search.setVisibility(View.GONE);

        return inflater.inflate(R.layout.fragment_create_coffee_review, container, false);


    }

}
