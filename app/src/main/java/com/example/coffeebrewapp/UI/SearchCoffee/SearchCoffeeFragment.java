package com.example.coffeebrewapp.UI.SearchCoffee;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

import java.util.ArrayList;
import java.util.List;

public class SearchCoffeeFragment extends Fragment implements CoffeeProductAdapter.OnListItemClickListener {


    private SearchCoffeeViewModel viewModel;
    public static FloatingActionButton fab_add;

    private RecyclerView recyclerView;
    private EditText sortCoffee;
    private List<CoffeeProduct> coffeeList;
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
        fab_add.show(); //Hiding the search button whilst showing the add button
        MainActivity.fab_search.hide();
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
                filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Recycler stuff
        recyclerView = layout.findViewById(R.id.search_page_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.hasFixedSize();

        //TODO: Set coffeelist from viewmodel
        //coffeeList = viewModel.getAllCoffeeProducts().observeForever();

        coffeeAdapter = new CoffeeProductAdapter(coffeeList, this);

        recyclerView.setAdapter(coffeeAdapter);


        return layout;
    }

    private void filter (String text)
    {
        ArrayList<CoffeeProduct> filteredList = new ArrayList<>();

        for (CoffeeProduct coffee: coffeeList)
        {

            if (coffee.getCoffeeName().toLowerCase().contains(text.toLowerCase()))
            {
            filteredList.add(coffee);
            }
        }
        coffeeAdapter.filterList(filteredList);
    }

    @Override
    public void onClick(int position) {
        //Creates mini test product
        //TODO: DELETE this stuff
        CoffeeProduct coffeeProduct = coffeeList.get(position);
        saveTestItem(coffeeProduct);

        //TODO: Fix it so it sends the position to SearchSelectedCoffeeFragment <-- That name tho


        MainActivity.navController.navigate(R.id.nav_search_selected_coffee);

    }

    private void saveTestItem(CoffeeProduct product)
    {
        viewModel.saveTestProduct(product);
    }

}
