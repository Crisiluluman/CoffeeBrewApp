package com.example.coffeebrewapp.UI.SearchCoffee;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProduct;
import com.example.coffeebrewapp.Data.CoffeProduct.CoffeeProductAdapter;
import com.example.coffeebrewapp.UI.Main.MainActivity;
import com.example.coffeebrewapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SearchCoffeeFragment extends Fragment implements CoffeeProductAdapter.OnListItemClickListener {


    private SearchCoffeeViewModel viewModel;
    public static FloatingActionButton fab_add;

    private RecyclerView recyclerView;
    private EditText sortCoffee;
    private CoffeeProductAdapter coffeeAdapter;


    //Test one product
    CoffeeProduct testCoffee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_search_coffee, container, false);
        viewModel = new ViewModelProvider(this).get(SearchCoffeeViewModel.class);

        //Getting single product test
        viewModel.init();

        // Floating button to add review page
        fab_add = layout.findViewById(R.id.floating_add_button);

        fab_add.show(); //Shows the CreateCoffeeButton onCreateView
        fab_add.setOnClickListener(v -> {
            MainActivity.navController.navigate(R.id.nav_add_coffee_review);
        });

        //Setting sort text
        sortCoffee = layout.findViewById(R.id.sort_bar_coffee);
        sortCoffee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                coffeeAdapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Recycler stuff
        recyclerView = layout.findViewById(R.id.search_page_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.hasFixedSize();

        coffeeAdapter = new CoffeeProductAdapter(this, getLayoutInflater().getContext());

        //viewModel.getAllCoffeeProducts().observe(getViewLifecycleOwner(), coffeeAdapter::updatedList);
        viewModel.getAllCoffeeProducts().observeForever(coffeeAdapter::updatedList); //Needs to be initilized both at Create and onStart

        recyclerView.setAdapter(coffeeAdapter);

        coffeeAdapter.notifyDataSetChanged();
        return layout;
    }



    @Override
    public void onResume() {
        fab_add.show(); //Shows the CreateCoffeeButton onResume of Fragment
        viewModel.getAllCoffeeProducts().observeForever(coffeeAdapter::updatedList);
        super.onResume();
    }

    @Override
    public void onPause() {
        fab_add.hide(); //Hides the CreateCoffeeButton on pausing of fragment
        super.onPause();
    }

    @Override
    public void onClick(int position) {
        String name = coffeeAdapter.getProduct(position).getCoffeeName();
        viewModel.setCoffeeFromSearch(name);
        MainActivity.navController.navigate(R.id.nav_search_selected_coffee);
    }

    public void filterBook(CharSequence s){

    }
}
